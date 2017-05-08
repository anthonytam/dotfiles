##
## S E T T I N G S
##

# Enable colors
autoload -U colors && colors

# enable colored output from ls, etc. on FreeBSD-based systems
export CLICOLOR=1

# awesome cd movements from zshkit
setopt autocd autopushd pushdminus pushdsilent pushdtohome cdablevars
DIRSTACKSIZE=5

# Enable extended globbing
setopt extendedglob

# Allow [ or ] whereever you want
unsetopt nomatch

# Setup zsh path
fpath=(
    ~/.zsh/completion/
    ~/.zsh/prompt/
    $fpath)

autoload -Uz compinit
compinit

# Set emacs as default editor
export EDITOR='vim'
export VISUAL='vim'

## History
HISTFILE=$HOME/.zhistory       # enable history saving on shell exit
setopt APPEND_HISTORY          # append rather than overwrite history file.
HISTSIZE=1200                  # lines of history to maintain memory
SAVEHIST=1000                  # lines of history to maintain in history file.
setopt HIST_EXPIRE_DUPS_FIRST  # allow dups, but expire old ones when I hit HISTSIZE
setopt EXTENDED_HISTORY        # save timestamp and runtime information

# Adds case insensitivity
zstyle ':completion:*' matcher-list 'm:{a-zA-Z}={A-Za-z}'
# Color completion folders
zstyle -e ':completion:*:default' list-colors 'reply=("${PREFIX:+=(#bi)($PREFIX:t)(?)*==34=34}:${(s.:.)LS_COLORS}")';
# Kill colors
zstyle ':completion:*:*:kill:*' list-colors '=(#b) #([0-9]#)*( *[a-z])*=34=31=33'
# Option colors
zstyle ':completion:*:options' list-colors '=^(-- *)=34'
# Highlights current option
zstyle ':completion:*' menu select

# Colorify man
function man() {
    env \
	LESS_TERMCAP_mb=$(printf "\e[1;31m") \
	LESS_TERMCAP_md=$(printf "\e[1;31m") \
	LESS_TERMCAP_me=$(printf "\e[0m") \
	LESS_TERMCAP_se=$(printf "\e[0m") \
	LESS_TERMCAP_so=$(printf "\e[1;44;33m") \
	LESS_TERMCAP_ue=$(printf "\e[0m") \
	LESS_TERMCAP_us=$(printf "\e[1;32m") \
	man "$@"
}

##
## T W E A K S
##
fancy-ctrl-z () {
    if [[ $#BUFFER -eq 0 ]]; then
        BUFFER="fg"
        zle accept-line
    else
        zle push-input
        zle clear-screen
    fi
}
zle -N fancy-ctrl-z
bindkey '^Z' fancy-ctrl-z

##
## K E Y B I N D I N G S
##
bindkey '^P' up-history
bindkey '^N' down-history

# ctrl-r starts searching history backward
bindkey '^r' history-incremental-search-backward

##
## A L I A S E S
##
# Misc
alias :q='exit'
alias grep='grep --color=always'
# List directory contents
alias lsa='ls -lah --color'
alias l='ls --color'
alias ll='ls -lh --color'
alias la='ls -A --color'
alias ls='ls --color'
# Push and pop directories on directory stack
alias -g ...='../..'
alias -g ....='../../..'
alias -g .....='../../../..'
alias -g ......='../../../../..'
# Emacs
alias e='emacs'
# Whats the tempreater weather man?
alias wttr='~/.bin/weather'
# Alias some stupid stuff
alias tim='echo "Where the fuck is tim?"'
alias gama='/bin/su'
alias su='echo "Please gama before su"'
# Just a git double check'er
alias justincase="git grep -Ei '(cancer|fuck|shit|cunt|cuck|whore|bastard|bitch|damn|dick|skunk|shrimp|r\.i\.p|/rip/)' $(git rev-list --all) | cut -d ':' -f 3 | sort | uniq"

##
## P R O M P T
##
PROMPT_STATUS="\`$SMILE\`"
_newline=$'\n'
_lineup=$'\e[1A'
_linedown=$'\e[1B'

# * There are uncommitted changes.
# ? There are files git doesn't know about.
# ➚ There are commits that haven't been pushed yet.
# ☰ There are stashed files.
# ⌥ There are branches other than master.
# ® There are remote repositories other than origin configured.
source ~/.zsh/git-prompt.zsh 

function preexec() {
    echo
}
function precmd() {
    echo
    PSVAR=`git_prompt_precmd`
}

PROMPT="%F{red}%n%F{white}@%F{green}%m %F{blue}%~ ${_newline}%F{white}$ "
RPROMPT='%{${_lineup}%}%F{red}%(?..%? )%F{yellow}%v%F{white}`jobs %% 2> /dev/null | cut -d " " -f6` [`date +%H:%M:%S`]%{${_linedown}%}'
setopt promptsubst

# Delay of 0.1 seconds
export KEYTIMEOUT=1

##
### P A T H
##
export PATH="$HOME/.bin:$PATH"

source ~/.zsh/zsh-syntax-highlighting/zsh-syntax-highlighting.zsh

## VirtualEnv
export WORKON_HOME=$HOME/.virtualenvs
source /usr/bin/virtualenvwrapper.sh
