#!/bin/bash

#
# SYSTEM INSTALLER
#
# This script will perform a system setup of a clean install of antergos (Arch) linux.
# Installes core packages for my use and setup's dot files
#

#
# FETCH AND UPDATE
#
yaourt -Syu --noconfirm

#
# WINDOW MANAGER
#
echo 1 | yaourt i3-gaps-git --noconfirm
echo 1 | yaourt i3status --noconfirm
echo 1 | yaourt dmenu --noconfirm

#
# EMACS (From source)
#
echo 1 | yaourt emacs-git --noconfirm

#
# VIM
#
echo 1 | yaourt gvim --noconfirm

#
# GOOGLE CHROME
#
echo 1 | yaourt google-chrome --noconfirm

#
# DOT FILES
#
read -p "Run dotfiles setup? (y/n) " yesorno
case "$yesorno" in
    y)
        bash setup.sh;;
    *)
        echo "Skipping dotfiles.";;
esac
