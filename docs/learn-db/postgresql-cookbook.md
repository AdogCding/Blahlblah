## Postgresql使用备忘录

### 安装教程
前提：使用Ubuntu环境

按照[官网指引](https://www.postgresql.org/download/linux/ubuntu/)，更新下载源后安装下载
```Shell
# Create the file repository configuration:
sudo sh -c 'echo "deb https://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'

# Import the repository signing key:
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -

# Update the package lists:
sudo apt-get update

# Install the latest version of PostgreSQL.
# If you want a specific version, use 'postgresql-12' or similar instead of 'postgresql':
sudo apt-get -y install postgresql
```
登录到`postgres`管理员创建低权用户，详情请见[网站](https://stackoverflow.com/questions/2172569/how-to-login-and-authenticate-to-postgresql-after-a-fresh-install)
这里使用文中的第二个方法
```Shell
### login as admin
sudo -u postgres psql postgres


### create role and database
CREATE ROLE myuser LOGIN PASSWORD 'mypass';
CREATE DATABASE mydatabase WITH OWNER = myuser;

### login in as whom you created
psql -h localhost -d mydatabase -U myuser -p 5432

### don't use a very complex password for ur very simple database
### if you forget ur password
ALTER USER yourusername WITH PASSWORD 'yournewpass';
```
修改配置允许第三方ssh连接，原文见[网站](https://stackoverflow.com/questions/38466190/cant-connect-to-postgresql-on-port-5432)

修改`postgresql.conf`
>listen_addresses = '*'

修改`pg_hba.conf`，增加一行
```Shell
host    mydatabase         myuser         192.168.1.0/24        md5
``` 
重启服务
```Shell
sudo service postgresql restart
```
或者直接用高权用户重新读取配置，不过我没有试过是不是可行[原文](https://github.com/hapostgres/pg_auto_failover/issues/67)
```sSQL
select SELECT pg_reload_conf();
```