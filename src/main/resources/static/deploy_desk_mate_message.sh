#!/bin/sh
#set -x
environment="prod"
dir="/sunlands/workspace/desk_mate_deliver/"
logsdir="/sunlands/logs/"
appsdir="/sunlands/apps/"
applicationName="desk-mate-message"
packageFlag=1
declare -A modelType

modelType[desk-mate-message]=web;

function mergeCode { 
        echo "mergeCode";
        cd ${dir}${applicationName};
        pwd
        git merge origin/${branchName};
        cd ..;
}

function updateCode {
        echo "更新代码"
        cd ${dir}${applicationName}
        git fetch
        cd ..
}
function innerPackage {
        echo "编译发布包"
        cd ${dir}
        mvn clean install -Dmaven.test.skip=true -U
        cd ..
}

function package {
         updateCode;
         mergeCode;
         innerPackage;
}


function backWeb {
         rm -f  /sunlands/apps/${modelName}.jar
        cp /sunlands/workspace/desk_mate_deliver/${applicationName}/target/${modelName}.jar /sunlands/apps/
        #mv ${appsdir}${modelName}.jar ${appsdir}backup/${modelName}.jar_`date +%Y%m%d%H%M`    
}

function stopWeb {
        SERVER_PID=`ps -ef | grep "${modelName}.jar" | grep -v "grep" | awk '{print $2}'`
        echo "server pid is ${SERVER_PID}"
        if [ -n "$SERVER_PID" ]; then
                kill -9 ${SERVER_PID}
                echo "${SERVER_PID} is killed!"
        fi
}

function startWeb {
nohup java -Dspring.profiles.active=test -server -Xms256m -Xmx512m -jar /sunlands/apps/${applicationName}.jar  >> /sunlands/logs/${applicationName}.log 2>&1 &
}
function deployWeb {
        #backWeb;
        stopWeb;
        backWeb;
        startWeb;
}

function deploy {

        if [ "${modelType[${modelName}]}" == "web" ]; then
                deployWeb;
        else
                echo "模块名${modelName}没有注册"
                exit 0
        fi
}


if [ "$1" = "-c" ]; then
  packageFlag=1
  shift
fi

modelName=desk-mate-message
branchName="test";
#branchName=$2
#if [ "${branchName}V" == "V" ]; then
#    branchName="master";
#fi
echo "你正在在 ${environment} 环境部署应用${dir}${applicationName}模块,分支名称${branchName}"
if [ "${packageFlag}" == "1" ]; then
        package

fi
if [ "${modelName}V" != "V" ]; then
 deploy ${modelName}
fi
