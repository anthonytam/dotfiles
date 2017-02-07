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
echo 1 | yaourt docbook-xsl --noconfirm
echo 1 | yaourt i3-gaps-git --noconfirm
echo 1 | yaourt i3status --noconfirm
echo 1 | yaourt dmenu --noconfirm
echo 1 | yaourt i3lock-fancy-git --noconfirm

#
# PINKY CONTROLS
#
cd ~
git clone https://github.com/anthonytam/PinkyCtrls.git
cd PinkyCtrls
make
sudo make install

#
# EMACS (From source)
#
echo 1 | yaourt emacs-git --noconfirm
echo 1 | yaourt aspell-en --noconfirm
echo 1 | yaourt texlive-core --noconfirm
echo 1 | yaourt texlive-latexextra --noconfirm
echo 1 | yaourt texlive-pictures --noconfirm

#
# VIM
#
echo 1 | yaourt gvim --noconfirm

#
# ZSH
#
echo 2 | yaourt zsh

#
# JAVA JDK & IDE & ANDROID
#
echo 1 | yaourt jdk8-openjdk --noconfirm
echo 1 | yaourt intellij-idea-ce --noconfirm
echo 1 | yaourt android-sdk --noconfirm
echo 1 | yaourt android-studio --noconfirm
#
# GOOGLE CHROME
#
echo 1 | yaourt google-chrome --noconfirm

#
# SPOTIFY
#
echo 33 | yaourt spotify --noconfirm

#
# DROP BOX
#
echo 1 | yaourt dropbox --noconfirm

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

read -p "Reboot? (y/n) " yesorno
case "$yesorno" in
    y)
        reboot;;
esac
