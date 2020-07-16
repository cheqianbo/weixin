#! /bin/bash
# 的jar放同级目录下即可，只能有一个jar文件22222
source /etc/profile
source ~/.bash_profile
CURRENT_PATH=$(cd "$(dirname "$0")"; pwd)
JAR=$(find $CURRENT_PATH -maxdepth 1 -name "*.jar")
JAVA_OPTS="-Xms128M -Xmx256M -XX:PermSize=64M -XX:MaxPermSize=128M"
PID=$(ps -ef | grep $JAR | grep -v grep | awk '{ print $2 }')

case "$1" in
    start)
        if [ ! -z "$PID" ]; then
            echo "$JAR 已经启动，进程号: $PID"
        else
            echo -n -e "启动 $JAR ... \n"
            nohup java -javaagent:"/data/master/apps/iast/rasp/rasp.jar" $JAVA_OPTS -jar $JAR >/dev/null 2>&1 &
            if [ "$?"="0" ]; then
                echo "启动完成，请查看日志确保成功"
            else
                echo "启动失败"
            fi
        fi
        ;;
    stop)
        if [ -z "$PID" ]; then
            echo "$JAR 没有在运行，无需关闭"
        else
            echo "关闭 $JAR ..."
	          kill -9 $PID
            if [ "$?"="0" ]; then
                echo "服务已关闭"
            else
                echo "服务关闭失败"
            fi
        fi
        ;;
    restart)
        ${0} stop
        ${0} start
        ;;
    status)
        if [ ! -z "$PID" ]; then
            echo "$JAR 正在运行"
        else
            echo "$JAR 未在运行"
        fi
        ;;
  *)
    echo "Usage: ./ {start|stop|restart|status}" >&2
        exit 1
esac
