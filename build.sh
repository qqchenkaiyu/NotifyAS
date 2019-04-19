#! /bin/bash

deploy_dir="deploy/NotifyAS"

# cleanup
rm -f ${deploy_dir}/*.jar
#### 添加内容开始>>>> ####
  
resource_dir="src/main/resources"
 
if [ ! -d "${resource_dir}" ]; then
    mkdir ${resource_dir}
else
    if [ -f "${resource_dir}/version.properties" ]; then
        rm ${resource_dir}/version.properties
    fi
fi
 
if [ $# > 0 ]; then
    for i in $@; do
        echo $i >> ${resource_dir}/version.properties
    done
fi
 
echo "buildTime=$(date '+%Y-%m-%d %H:%M:%S')" >> ${resource_dir}/version.properties
  
#### <<<<添加内容结束 ####
mvn -f pom.xml -Dmaven.test.skip=true clean install
if [ $? -eq 0 ]; then
    mv target/*.jar ${deploy_dir}
fi
rm -rf target
cp *.txt ${deploy_dir}/
