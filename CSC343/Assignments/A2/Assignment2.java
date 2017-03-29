import javax.print.attribute.standard.PrinterState;
import java.sql.*;

// Remember that part of your mark is for doing as much in SQL (not Java) 
// as you can. At most you can justify using an array, or the more flexible
// ArrayList. Don't go crazy with it, though. You need it rarely if at all.
import java.util.ArrayList;

public class Assignment2 {

    // A connection to the database
    Connection connection;

    Assignment2() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connects to the database and sets the search path.
     * 
     * Establishes a connection to be used for this session, assigning it to the
     * instance variable 'connection'. In addition, sets the search path to
     * markus.
     * 
     * @param url
     *            the url for the database
     * @param username
     *            the username to be used to connect to the database
     * @param password
     *            the password to be used to connect to the database
     * @return true if connecting is successful, false otherwise
     */
    public boolean connectDB(String URL, String username, String password) {
        try {
            connection = DriverManager.getConnection(URL, username, password);
            String queryString = "SET search_path TO markus";
            PreparedStatement pStatement = connection.prepareStatement(queryString);
            int result = pStatement.executeUpdate();
	    return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Closes the database connection.
     * 
     * @return true if the closing was successful, false otherwise
     */
    public boolean disconnectDB() {
        try {
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Assigns a grader for a group for an assignment.
     * 
     * Returns false if the groupID does not exist in the AssignmentGroup table,
     * if some grader has already been assigned to the group, or if grader is
     * not either a TA or instructor.
     * 
     * @param groupID
     *            id of the group
     * @param grader
     *            username of the grader
     * @return true if the operation was successful, false otherwise
     */
    public boolean assignGrader(int groupID, String grader) {
        try {
            String queryString = "SELECT * FROM AssignmentGroup WHERE group_id = ?";
            PreparedStatement pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, groupID);
	    ResultSet rs = pStatement.executeQuery();
            if (!rs.next()) {
		System.out.println("here?");
                return false;
	    }

            queryString = "SELECT * FROM Grader WHERE group_id = ?";
            pStatement = connection.prepareStatement(queryString);
	    pStatement.setInt(1, groupID);
            rs = pStatement.executeQuery();
            if (rs.next()) {
		System.out.println("here??");
                return false;
	    }

            queryString = "SELECT username FROM MarkusUser WHERE (type = 'TA' OR type = 'instructor') AND username = ?";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setString(1, grader);
	    rs = pStatement.executeQuery();
            if (!rs.next()) {
		System.out.println("here???");
                return false;
            }

            queryString = "INSERT INTO Grader VALUES (?, ?)";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, groupID);
            pStatement.setString(2, grader);
            int result = pStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
	    return false;
        }
    }

    /**
     * Adds a member to a group for an assignment.
     * 
     * Records the fact that a new member is part of a group for an assignment.
     * Does nothing (but returns true) if the member is already declared to be
     * in the group.
     * 
     * Does nothing and returns false if any of these conditions hold: - the
     * group is already at capacity, - newMember is not a valid username or is
     * not a student, - there is no assignment with this assignment ID, or - the
     * group ID has not been declared for the assignment.
     * 
     * @param assignmentID
     *            id of the assignment
     * @param groupID
     *            id of the group to receive a new member
     * @param newMember
     *            username of the new member to be added to the group
     * @return true if the operation was successful, false otherwise
     */
    public boolean recordMember(int assignmentID, int groupID, String newMember) {
        try {
            String queryString = "SELECT * FROM Membership WHERE group_id = ? AND username = ?";
            PreparedStatement pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, groupID);
            pStatement.setString(2, newMember);
            ResultSet rs = pStatement.executeQuery();
            if (rs.next())
                return true;

            queryString = "SELECT * FROM Assignment WHERE assignment_id = ?";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, assignmentID);
	    rs = pStatement.executeQuery();
            if (!rs.next())
                return false;

            queryString = "SELECT * FROM AssignmentGroup WHERE group_id = ?";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, groupID);
	    rs = pStatement.executeQuery();
            if (!rs.next())
                return false;

            queryString = "SELECT * FROM MarkusUser WHERE username = ? AND type = 'student'";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setString(1, newMember);
	    rs = pStatement.executeQuery();
            if (!rs.next())
                return false;

            queryString = "SELECT group_max FROM Assignment WHERE assignment_id = ?";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, assignmentID);
	    rs = pStatement.executeQuery();
            rs.next();
            int maxMembers = rs.getInt("group_max");

            queryString = "SELECT COUNT(DISTINCT username) AS numMembers FROM Membership WHERE group_id = ?";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setInt(1, groupID);
	    rs = pStatement.executeQuery();
            rs.next();
            if (rs.getInt("numMembers") == maxMembers)
                return false;

            queryString = "INSERT INTO Membership VALUES (?, ?)";
            pStatement = connection.prepareStatement(queryString);
            pStatement.setString(1, newMember);
            pStatement.setInt(2, groupID);
            int result = pStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Creates student groups for an assignment.
     * 
     * Finds all students who are defined in the Users table and puts each of
     * them into a group for the assignment. Suppose there are n. Each group
     * will be of the maximum size allowed for the assignment (call that k),
     * except for possibly one group of smaller size if n is not divisible by k.
     * Note that k may be as low as 1.
     * 
     * The choice of which students to put together is based on their grades on
     * another assignment, as recorded in table Results. Starting from the
     * highest grade on that other assignment, the top k students go into one
     * group, then the next k students go into the next, and so on. The last n %
     * k students form a smaller group.
     * 
     * In the extreme case that there are no students, does nothing and returns
     * true.
     * 
     * Students with no grade recorded for the other assignment come at the
     * bottom of the list, after students who received zero. When there is a tie
     * for grade (or non-grade) on the other assignment, takes students in order
     * by username, using alphabetical order from A to Z.
     * 
     * When a group is created, its group ID is generated automatically because
     * the group_id attribute of table AssignmentGroup is of type SERIAL. The
     * value of attribute repo is repoPrefix + "/group_" + group_id
     * 
     * Does nothing and returns false if there is no assignment with ID
     * assignmentToGroup or no assignment with ID otherAssignment, or if any
     * group has already been defined for this assignment.
     * 
     * @param assignmentToGroup
     *            the assignment ID of the assignment for which groups are to be
     *            created
     * @param otherAssignment
     *            the assignment ID of the other assignment on which the
     *            grouping is to be based
     * @param repoPrefix
     *            the prefix of the URL for the group's repository
     * @return true if successful and false otherwise
     */
    public boolean createGroups(int assignmentToGroup, int otherAssignment,
            String repoPrefix) {
        // Replace this return statement with an implementation of this method!
        return false;
    }

    public static void main(String[] args) {
	try {
		Assignment2 A2 = new Assignment2();
    		A2.connectDB("jdbc:postgresql://localhost:5432/csc343h-tamanth2", "tamanth2", "");
		boolean result = A2.assignGrader(1, "t1");
        	System.out.println(result ? "Yes" : "no");
    	} catch (SQLException e) {
		e.printStackTrace();
	}
    }
}
