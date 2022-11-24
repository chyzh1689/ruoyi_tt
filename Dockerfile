FROM openjdk:8
# author
MAINTAINER tt
# 挂载目录
VOLUME /home/tt
# 创建目录
RUN mkdir -p /home/tt
# 指定路径
WORKDIR /home/tt
# 复制jar文件到路径
COPY ./*.jar /home/tt/tt.jar
# 设定时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 启动应用
ENTRYPOINT ["java","-jar","tt.jar"]
