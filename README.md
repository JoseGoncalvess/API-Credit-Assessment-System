<h1 align="center">API para Sistema de Avaliação de Créditos</h1>

<h3>Resumo</h3>
<div align="center"><img src="https://assets.dio.me/G-J57IlKQH1VFzljwoJmP9CjR3qiNtGmQCYsUNr1Bn0/f:webp/h:120/q:80/L3RyYWNrcy8yYWIyNzMwMy1jY2JlLTQ2YTUtODEzOC1jNTBjMjkxNzU5NjAucG5n" height="120" weigth="120" alt="logo Desenvolvimento Backend com Kotlin"></div>
Este repositório foi criado para fins de estudo, então contribua com ele caso queira, com esse projeto pude validar habilidades adquiridas durante
a trajetória do curso [Desenvolvimento Backend com Kotlin](https://web.dio.me/track/desenvolvimento-backend-com-kotlin).
<br>

<h2 align="center">TECNOLOGIAS E HABILIDADES DESENVOLVIDAS:</h2>
<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/kotlin/kotlin-original.svg" height="40" alt="kotlin logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg" height="40" alt="intellij logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://avatars.githubusercontent.com/u/11459762?s=280&v=4" height="40" alt="H2 logo"  />
  <img width="12" />
  <img src="https://upload.wikimedia.org/wikipedia/commons/e/e1/Flyway_logo.svg" height="40" alt="Flywy logo"  />
</div>

###


<p>Fique a vontade para clonar o repositorio, claro caso qeuira conhecer masi o projeto original logo baixo tem o link certinho!</p>
<br>


<h2 align="center">CASE</h2>
<p>Uma empresa de empréstimo precisa criar um sistema de análise de solicitação de crédito. Sua tarefa será criar uma <strong>API REST SPRING BOOT E KOTLIN</strong> 🍃💜 para a empresa fornecer aos seus clientes as seguintes funcionalidades:</p>
<hr>
<h2 align="center">ESPECIFICAÇÕES</h2>
<ul>
<li><h3>Cliente (Customer):</h3>
  <ul>
    <li><strong>Cadastrar:</strong>
         <ol>
            <li><strong>Request: </strong><em>firstName, lastName, cpf, income, email, password, zipCode e street</em></li>
            <li><strong>Response: </strong><em>String</em></li>
        </ol>
    </li>
  <li><strong>Editar cadastro:</strong>
    <ol>
      <li><strong>Request: </strong><em>id, firstName, lastName, income, zipCode, street</em></li>
      <li><strong>Response: </strong><em>firstName, lastName, income, cpf, email, income, zipCode, street</em></li>
    </ol>
  </li>  
  <li><strong>Visualizar perfil:</strong>
    <ol>
      <li><strong>Request: </strong> <em>id</em></li>
      <li><strong>Response: </strong><em>firstName, lastName, income, cpf, email, income, zipCode, street</em></li>
    </ol> 
  </li>
  <li><strong>Deletar cadastro:</strong>
    <ol>
      <li><strong>Request: </strong><em>id</em></li>
      <li><strong>Response: </strong><em>sem retorno</em></li>
    </ol>
  </li>
  </ul>
  </li>
  <li><h3>Solicitação de Empréstimo (Credit):</h3>
  <ul>
    <li><strong>Cadastrar:</strong>
         <ol>
            <li><strong>Request: </strong><em>creditValue, dayFirstOfInstallment, numberOfInstallments e customerId</em></li>
            <li><strong>Response: </strong><em>String</em></li>
        </ol>
    </li>
    <li><strong>Listar todas as solicitações de emprestimo de um cliente:</strong>
    <ol>
      <li><strong>Request: </strong><em>customerId</em></li>
      <li><strong>Response: </strong><em>creditCode, creditValue, numberOfInstallment</em></li>
    </ol> 
    </li>
    <li><strong>Visualizar um emprestimo:</strong>
    <ol>
      <li><strong>Request: </strong><em>customerId e creditCode</em></li>
      <li><strong>Response: </strong><em>creditCode, creditValue, numberOfInstallment, status, emailCustomer e incomeCustomer</em></li>
    </ol> 
    </li>
</ul>

<figure>
<p align="center">
  <img src="https://i.imgur.com/7phya16.png" height="450" width="650" alt="API para Sistema de Avaliação de Créditos"/><br>
  Diagrama UML Simplificado de uma API para Sistema de Avaliação de Crédito
</p>
</figure>
<figure>
<p align="center">
  <img src="https://i.imgur.com/1Ea5PH3.png" height="350" width="600" alt="Arquitetura em 3 camadas Projeto Spring Boot"/><br>
  Arquitetura em 3 camadas Projeto Spring Boot
</p>
</figure>

<hr>
<h3>Links Úteis</h3>
<ul>
  <li>https://start.spring.io/#!type=gradle-project&language=kotlin&platformVersion=3.0.3&packaging=jar&jvmVersion=17&groupId=me.dio&artifactId=credit-application-system&name=credit-application-system&description=Credit%20Application%20System%20with%20Spring%20Boot%20and%20Kotlin&packageName=me.dio.credit-application-system&dependencies=web,validation,data-jpa,flyway,h2</li>
  <li>https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/common-application-properties.html</li>
  <li>https://medium.com/cwi-software/versionar-sua-base-de-dados-com-spring-boot-e-flyway-be4081ddc7e5</li>
  <li>https://strn.com.br/artigos/2018/12/11/todas-as-anota%C3%A7%C3%B5es-do-jpa-anota%C3%A7%C3%B5es-de-mapeamento/</li>
  <li>https://pt.wikipedia.org/wiki/Objeto_de_Transfer%C3%AAncia_de_Dados</li>
  <li>https://pt.wikipedia.org/wiki/CRUD</li>
  <li>https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords</li>
  <li>https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.at-query</li>
  <li>https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#glossary</li>  
</ul>

<hr>





<h3>Autor E Instrutora</h3>

<a href="https://www.instagram.com/camimi_la/">
 <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/64323124?v=4" width="100px;" alt=""/>
 <br />
 <sub><b>Camila Cavalcante</b></sub></a> <a href="https://www.instagram.com/camimi_la/" title="Instagram"></a>

Feito com ❤️ por Cami-la 👋🏽 Entre em contato!

[![Linkedin Badge](https://img.shields.io/badge/-Camila-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/cami-la/)](https://www.linkedin.com/in/cami-la/)
[![Gmail Badge](https://img.shields.io/badge/-camiladsantoscavalcante@gmail.com-c14438?style=flat-square&logo=Gmail&logoColor=white&link=mailto:camiladsantoscavalcante@gmail.com)](mailto:camiladsantoscavalcante@gmail.com)
