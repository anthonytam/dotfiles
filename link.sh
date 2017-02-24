#!/bin/bash

cd "$( dirname "${BASH_SOURCE[0]}" )"
#printf "Linking dotfiles from \"$(pwd)\"\n\n"


if [[ $1 = "-u" ]] || [[ $1 = "--uninstall" ]]
then
	for ITEM in `ls -d */`;
	do
		( stow -D $ITEM )
	done
elif [[ $1 = "-h" ]] || [[ $1 = "--help" ]]
then
	printf "Tam's dotfile installer (V1)\n\n"
	printf "USAGE:\n"
	printf "\t${0} [FLAGS]\n\n"
	printf "FLAGS:\n\n"
	printf "\t-u, --uninstall\tRemoves all dotfile symlinks\n"
	printf "\t-d, --force\tForces the install of all dotfiles (No prompts)\n"
	printf "\t-h, --help\tDisplay this message\n\n"
	printf "Report bugs/problems by creating an issue on github\n"
	printf "https://github.com/anthonytam/DotFiles\n"
else
	# If the current shell is not set to zsh
	if [[ "$(getent passwd $USER | cut -d: -f7)" = *"zsh"* ]]
	then
		printf "ZSH is already set as default shell...\n"
	else
		if command -v zsh >/dev/null 2>&1
		then
			if [[ $1 = "-f" ]] || [[ $1 = "--force" ]]
			then
				ZSH_PATH=`which zsh`
				printf "Setting $ZSH_PATH as the default shell\n"
				chsh $USER -s $ZSH_PATH
			else
				read -p "Set ZSH as default shell? (y/n) " USRINPUT
				case "$USRINPUT" in
					y) 
						ZSH_PATH=`which zsh`
						printf "Setting $ZSH_PATH as the default shell\n"
						chsh $USER -s $ZSH_PATH
						;;
					*) 
						printf "Will not set ZSH as the default shell\n"
						;;
				esac
			fi
		else
			printf "Warning: ZSH is not installed\n"
		fi
	fi

	for ITEM in `ls -d */`;
	do
		if [[ $1 = "-f" ]] || [[ $1 = "--force" ]]
		then
			printf "Setting up $ITEM\n"
			( stow $ITEM )
		else
			read -p "Setup config for $ITEM? (y/n) " USRINPUT
			case "$USRINPUT" in
				y)
					( stow $ITEM )
					;;
				*)
					printf "Skipping $ITEM\n"
					;;
			esac
		fi
	done
fi
