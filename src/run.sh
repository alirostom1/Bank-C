#!/bin/bash


javac -cp .:io/github/alirostom1/bankc/drivers/mysql-connector-j-9.3.0.jar -d out io/github/alirostom1/bankc/Main.java

java -cp out:io/github/alirostom1/bankc/drivers/mysql-connector-j-9.3.0.jar io.github.alirostom1.bankc.Main