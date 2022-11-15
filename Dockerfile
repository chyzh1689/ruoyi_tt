FROM openjdk:8
# author
MAINTAINER ruoyi
# 挂载目录
VOLUME /home/ruoyi
# 创建目录
RUN mkdir -p /home/ruoyi
# 指定路径
WORKDIR /home/ruoyi
# 复制jar文件到路径
COPY ./*.jar /home/ruoyi/ruoyi.jar
# 启动应用
ENTRYPOINT ["java","-jar","ruoyi.jar"]
