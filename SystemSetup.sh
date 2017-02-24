#!/bin/bash

#
# SYSTEM INSTALLER
#
# This script will perform a system setup of a clean install of antergos (Arch) linux.
# Installes core packages for my use and setup dot files
#

printf "Enter your user password\n"
read -rsp "Password: " USRPASS 

#
# FETCH AND UPDATE
#
yaourt -Syu --noconfirm

# Gotta deal with them temp files somehow...
mkdir /tmp/systemsetuptmp
cd /tmp/systemsetuptmp

#
# WINDOW MANAGER (i3-gaps)
#
sudo -S pacman -S feh --noconfirm <<<$USRPASS
sudo -S pacman -S docbook-xsl --noconfirm <<<$USRPASS
yaourt -G i3-gaps-git
yaourt -P i3-gaps-git --noconfirm
sudo -S pacman -S i3status --noconfirm <<<$USRPASS
sudo -S pacman -S dmenu --noconfirm <<<$USRPASS
yaourt -G i3lock-fancy-git
yaourt -P i3lock-fancy-git --noconfirm

#
# PINKY CONTROLS
#
OLDDIR = pwd
cd ~
git clone https://github.com/anthonytam/PinkyCtrls.git
cd PinkyCtrls
make
sudo -S make install
cd $OLDDIR

#
# EMACS (From source)
#
yaourt -G emacs-git
yaourt -P emacs-git --noconfirm
sudo -S pacman -S aspell-en --noconfirm <<<$USRPASS
sudo -S pacman -S texlive-core --noconfirm <<<$USRPASS
sudo -S pacman -S texlive-latexextra --noconfirm <<<$USRPASS
sudo -S pacman -S texlive-pictures --noconfirm <<<$USRPASS

#
# VIM
#
sudo -S pacman -S gvim --noconfirm <<<$USRPASS

#
# ZSH
#
sudo -S pacman -S zsh --noconfirm <<<$USRPASS

#
# JAVA JDK & IDE & ANDROID
#
sudo -S pacman -S jdk8-openjdk --noconfirm <<<$USRPASS
yaourt -G intellij-idea-ce
cd intellij-idea-ce
makepkg -i --noconfirm
cd ..
yaourt -G android-sdk
cd android-sdk
makepkg -i --noconfirm
cd ..
yaourt -G android-studio
cd android-studio
makepkg -i --noconfirm
cd ..

#
# GOOGLE CHROME
#
yaourt -G google-chrome
cd google-chrome
makepkg -i --noconfirm
cd ..

#
# SPOTIFY
#
yaourt -G spotify
cd spotify
makepkg -i --noconfirm
cd ..

#
# DROP BOX
#
sudo -S pacman -S dropbox --noconfirm

#
# DOT FILES
#
read -p "Run dotfiles setup? (y/n) " yesorno
case "$yesorno" in
    y)
        bash link.sh;;
    *)
        echo "Skipping dotfiles.";;
esac

read -p "Reboot? (y/n) " yesorno
case "$yesorno" in
    y)
        reboot;;
esac
