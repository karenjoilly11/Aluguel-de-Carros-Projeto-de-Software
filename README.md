# Aluguel-de-Carros-Projeto-de-Software
Sistema de Aluguel de Carros desenvolvido em Java/Spring Boot com arquitetura MVC. O projeto contempla o gerenciamento completo de locações, incluindo CRUD de clientes com validação de rendimentos, autenticação de usuários (Clientes/Agentes) e fluxo de análise financeira, seguindo padrões de Engenharia de Software e modelagem UML.

# 👥 Equipe e Integrantes: 

* Josué Carlos Goulart dos Reis

* Karen Joilly Araujo Gregorio de Almeida

* Luiz Fernando Batista Moreira

# Professor Responsável: João Paulo Carneiro Aramuni

# Instituição: PUC Minas - Laboratório de Engenharia de Software

# 📋 Histórias de Usuário - Sistema de Aluguel de Carros

Este documento descreve as funcionalidades da sob a perspectiva dos usuários.

---

## 🚗 Módulo de Cadastro e Perfil (CRUD)

### US01 - Auto-cadastro de Cliente
**Como** um novo usuário,  
**Eu quero** me cadastrar no sistema informando meus dados pessoais (RG, CPF, Endereço, Profissão) e meus rendimentos,  
**Para que** eu possa estar habilitado a solicitar aluguéis de veículos.

* **Critérios de Aceite:**
    * O sistema deve permitir no máximo 3 rendimentos por cliente.
    * O CPF e RG devem ser únicos no sistema.
    * Todos os campos obrigatórios devem ser validados antes de salvar no banco de dados.

### US02 - Gestão de Dados Cadastrais
**Como** um cliente cadastrado,  
**Eu quero** alterar minhas informações de endereço e profissão,  
**Para que** meu perfil esteja atualizado para futuras análises financeiras.

---

## 🔑 Módulo de Autenticação

### US03 - Login de Usuário
**Como** um usuário (Cliente ou Agente),  
**Eu quero** realizar a autenticação utilizando e-mail e senha,  
**Para que** eu possa acessar as funcionalidades restritas ao meu perfil.

---

## 📈 Módulo de Aluguel e Avaliação

### US04 - Introdução de Pedido de Aluguel
**Como** um cliente autenticado,  
**Eu quero** selecionar um automóvel e definir o período de locação,  
**Para que** minha solicitação seja enviada para análise dos agentes.

### US05 - Avaliação Financeira (Parecer do Agente)
**Como** um Agente (Banco ou Empresa),  
**Eu quero** analisar os pedidos de aluguel e registrar um parecer (Aprovado/Rejeitado),  
**Para que** o processo de contratação possa prosseguir ou ser encerrado.

---

## 📜 Módulo de Contratos

### US06 - Assinatura de Contrato
**Como** um cliente com pedido aprovado,  
**Eu quero** visualizar os termos do contrato e registrar minha assinatura digital,  
**Para que** o aluguel seja formalizado e o veículo reservado.
