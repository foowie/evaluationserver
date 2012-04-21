#!/bin/sh
### BEGIN INIT INFO
# Provides:          evaluationserver-web
# Required-Start:    
# Required-Stop:     
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Evaluation server
# Description:       This file should be used to construct scripts to be
#                    placed in /etc/init.d.
### END INIT INFO

# Author: Daniel Robenek <danrob@seznam.cz>

# Do NOT "set -e"
# PATH should only include /usr/* if it runs after the mountnfs.sh script
PATH=/sbin:/usr/sbin:/bin:/usr/bin
DESC="Evaluation server - web"
NAME=evaluationserver-web
DAEMON=/usr/sbin/$NAME
DAEMON_ARGS=""
PIDFILE=/var/run/$NAME.pid
SCRIPTNAME=/etc/init.d/$NAME

# Exit if the package is not installed
[ -x "$DAEMON" ] || exit 0

# Read configuration variable file if it is present
[ -r /etc/default/$NAME ] && . /etc/default/$NAME

# Load the VERBOSE setting and other rcS variables
. /lib/init/vars.sh

# Define LSB log_* functions.
# Depend on lsb-base (>= 3.2-14) to ensure that this file is present
# and status_of_proc is working.
. /lib/lsb/init-functions



case "$1" in
  start)
	log_daemon_msg "Starting $DESC" "$NAME", this takes about 30s
	cd /usr/share/evaluationserver/web
	play start
	;;
  stop)
	log_daemon_msg "Stopping $DESC" "$NAME"
	cd /usr/share/evaluationserver/web
	play stop
	;;
  status)
	cd /usr/share/evaluationserver/web
	play status
    ;;
  restart|force-reload)
	#
	# If the "reload" option is implemented then remove the
	# 'force-reload' alias
	#
	log_daemon_msg "Restarting $DESC" "$NAME"
	cd /usr/share/evaluationserver/web
	play stop
	play start
	;;
  *)
	echo "Usage: $SCRIPTNAME {start|stop|restart}" >&2
	exit 3
	;;
esac

:
