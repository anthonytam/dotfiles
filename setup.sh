#!/bin/bash

# Install dotfiles on the system for ease of switching computers.
# Prompts for each feature to be updated
#
# RIP Eli's script

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo -e Linking dotfiles at \"$DIR\" "\n"

# If the current shell is not set to zsh
if [[ "$(getent passwd $USER | cut -d: -f7)" = *"zsh"* ]]
then
	echo "zsh is already set as default shell... skipping config"
else
	if command -v zsh >/dev/null 2>&1
	then
		read -p "Set ZSH as default shell? (y/n) " yesorno
		case "$yesorno" in
			y) 
				ZSH_PATH=`which zsh`
				echo "Setting $ZSH_PATH as the default shell"
				chsh $USER -s $ZSH_PATH ;;
			*) echo "zsh was not set to the default shell" ;;
		esac
	else
		echo "Warning: zsh is not installed - defualt shell not be changed"
	fi
fi

##
## BASH
##
read -p "Set up bash config? (y/n) " yesorno
case "$yesorno" in
    y)
        rm $HOME/.bashrc
        ln -s $DIR/bash/.bashrc $HOME/.bashrc;;
    *)
        echo "Skipping bash." ;;
esac

##
## ZSH
##
read -p "Set up zsh config? (y/n) " yesorno
case "$yesorno" in
    y)
        rm "$HOME/.zshrc"
        rm -r "$HOME/.zsh"
        ln -s "$DIR/zsh/.zshrc" "$HOME/.zshrc"
        ln -s "$DIR/zsh/.zsh" "$HOME/.zsh";;
    *)
        echo "Skipping zsh." ;;
esac

##
## EMACS
##
read -p "Set up emacs config? (y/n) " yesorno
case "$yesorno" in
    y)
        rm -r "$HOME/.emacs.d"
        mkdir -p "$HOME/.emacs.d"
        ln -s "$DIR/emacs/init.el" "$HOME/.emacs.d/init.el"
        ln -s "$DIR/emacs/themes" "$HOME/.emacs.d/themes"
	ln -s "$DIR/emacs/README.org" "$HOME/.emacs.d/README.org";;
    *)
        echo "Skipping emacs." ;;
esac

##
## BIN Scripts
##
read -p "Set up bin scripts? (y/n) " yesorno
case "$yesorno" in
    y)
        rm -r "$HOME/.bin"
        ln -s "$DIR/.bin" "$HOME/.bin";;
    *)
        echo "Skipping bin scripts." ;;
esac

##
## GIT
##
read -p "Set up git config? (y/n) " yesorno
case "$yesorno" in
    y)
        rm "$HOME/.gitconfig"
        ln -s "$DIR/.gitconfig" "$HOME/.gitconfig";;
    *)
        echo "Skipping git." ;;
esac

##
## i3
##
read -p "Set up i3 config? (y/n) " yesorno
case "$yesorno" in
    y)
        rm "$HOME/.i3status.conf"
	rm "$HOME/.config/i3/config"
	mkdir -p "$HOME/.config/i3"
        ln -s "$DIR/i3/.i3status.conf" "$HOME/.i3status.conf"
	ln -s "$DIR/i3/config" "$HOME/.config/i3/config";;
    *)
        echo "Skipping i3." ;;
esac
