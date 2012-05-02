#!/bin/sh
### BEGIN INIT INFO
# Provides:          evaluationserver-server
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
DESC="Evaluation server"
NAME=evaluationserver-server
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
	echo -n "Starting $DESC: "
	start-stop-daemon --start --background --make-pidfile --pidfile $PIDFILE \
		--exec $DAEMON -- $DAEMON_ARGS
	echo "$NAME."
	;;
  stop)
	echo -n "Stopping $DESC: "
	start-stop-daemon --stop --oknodo --pidfile $PIDFILE
	rm -f $PIDFILE
	echo "$NAME."
	;;
  status)
	if [ -s $PIDFILE ]; then
            RUNNING=$(cat $PIDFILE)
            if [ -d /proc/$RUNNING ]; then
                echo "$NAME is running."
                exit 0
            fi

            # No such PID, or executables don't match
            echo "$NAME is not running, but pidfile existed."
            rm $PIDFILE
            exit 1
        else
            rm -f $PIDFILE
            echo "$NAME not running."
            exit 1
        fi
	;;
  restart|force-reload)
	echo -n "Restarting $DESC: "
	start-stop-daemon --stop --oknodo --pidfile $PIDFILE
	rm -f $PIDFILE
	sleep 1
	start-stop-daemon --start --background --make-pidfile --pidfile $PIDFILE \
		--exec $DAEMON -- $DAEMON_ARGS
	echo "$NAME."
	;;
  *)
	echo "Usage: $SCRIPTNAME {start|stop|status|restart}" >&2
	exit 3
	;;
esac

: