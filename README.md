# MovieAgent - 基于大模型的智能电影订票系统

## 项目简介
MovieAgent 是一个基于大模型与 Agent 架构实现的智能电影订票系统。  
系统通过接入阿里云百炼 Qwen3-Max 大模型，并结合 LangChain4j 框架，实现自然语言对话式电影查询与订票功能。用户可以通过对话的方式查询电影场次、预订电影票、取消订单等。

项目结合向量数据库与传统数据库，实现电影信息检索与业务数据管理：
- 使用 Pinecone 存储电影相关向量数据，实现语义检索能力
- 使用 MySQL 存储电影订票信息与业务数据
- 使用 MongoDB 存储用户对话记忆，实现多轮对话能力

该项目主要用于探索 **大模型 Agent 在真实业务场景中的应用方式**，并演示如何通过工具调用（Tool Calling）实现大模型与后端业务系统的结合。

---

## 技术栈
- Java
- Spring Boot
- LangChain4j
- 阿里云百炼 Qwen3-Max
- Pinecone（向量数据库）
- MySQL
- MongoDB

---

## 核心功能
- 自然语言电影查询
- 电影场次查询
- 智能电影订票
- 订单取消
- 多轮对话记忆
- Agent Tool 工具调用

---

## 项目结构
movieAgent
├─ movieAgent-ui 前端界面
├─ src 后端源码
├─ pom.xml Maven依赖配置
└─ README.md 项目说明

---

## 项目特点
- 基于 **LangChain4j Agent 架构**构建智能体
- 使用 **Tool Calling** 让大模型调用后端业务接口
- 结合 **向量数据库 + 传统数据库** 实现 AI + 业务系统融合
- 支持 **自然语言交互的电影订票流程**

---

## 运行环境
- JDK 17+
- Maven
- MySQL
- MongoDB
- Pinecone 向量数据库
- 阿里云百炼 API Key

---

## 项目目的
该项目主要用于学习和实践：
- 大模型应用开发
- Agent 架构设计
- RAG 与向量检索
- AI 与传统后端系统的结合
