module mux4to1(In0, In1, In2, In3, In4, In5, In6, In7, S, f);
	input In0, In1, In2, In3, In4, In5, In6, In7;
	input [7:0] S;
	output reg f;
	always @(*)
	begin
		case (S)
			0: f = In0;
			1: f = In1;
			2: f = In2;
			3: f = In3;
			4: f = In4;
			5: f = In5;
			6: f = In6;
			7: f = In7;
		endcase
	end
endmodule