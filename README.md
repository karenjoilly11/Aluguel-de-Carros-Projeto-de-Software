# 🚗 Aluguel de Carros - Projeto de Software

Sistema desenvolvido em **Java/Spring Boot** utilizando arquitetura **MVC**. O projeto contempla o gerenciamento completo de locações, incluindo CRUD de clientes com validação de rendimentos, autenticação de usuários (Clientes/Agentes) e fluxo de análise financeira, seguindo padrões de Engenharia de Software e modelagem UML.

---

## 👥 Equipe e Integrantes
* **Integrantes:**
  * Josué Carlos Goulart dos Reis
  * Karen Joilly Araujo Gregorio de Almeida
  * Luiz Fernando Batista Moreira
* **Professor Responsável:** João Paulo Carneiro Aramuni
* **Instituição:** PUC Minas - Laboratório de Engenharia de Software

---

## 📋 Histórias de Usuário 

Este documento descreve as funcionalidades do sistema sob a perspectiva dos diferentes atores envolvidos.

### 🚗 Módulo de Cadastro e Perfil (CRUD)

#### **US01 - Auto-cadastro de Clientes**
* **Como:** um novo usuário,
* **Eu quero:** me cadastrar no sistema informando meus dados pessoais (RG, CPF, Endereço, Profissão) e meus rendimentos,
* **Para que:** eu possa estar habilitado a solicitar aluguéis de veículos.
* **Critérios de Aceite:**
    * O sistema deve permitir no máximo 3 rendimentos por cliente.
    * O CPF e RG devem ser únicos no sistema.
    * Validação obrigatória de todos os campos antes da persistência.

#### **US02 - Gestão de Dados Cadastrais**
* **Como:** um cliente cadastrado,
* **Eu quero:** alterar minhas informações de endereço e profissão,
* **Para que:** meu perfil esteja atualizado para futuras análises financeiras.

---

### 🔑 Módulo de Autenticação

#### **US03 - Login de Usuário**
* **Como:** um usuário (Cliente ou Agente),
* **Eu quero:** realizar a autenticação utilizando e-mail e senha,
* **Para que:** eu possa acessar as funcionalidades restritas ao meu perfil.

---

### 📈 Módulo de Aluguel e Avaliação

#### **US04 - Introdução de Pedido de Aluguel**
* **Como:** um cliente autenticado,
* **Eu quero:** selecionar um automóvel e definir o período de locação,
* **Para que:** minha solicitação seja enviada para análise dos agentes.

#### **US05 - Avaliação Financeira (Parecer do Agente)**
* **Como:** um Agente (Banco ou Empresa),
* **Eu quero:** analisar os pedidos de aluguel e registrar um parecer (Aprovado/Rejeitado),
* **Para que:** o processo de contratação possa prosseguir ou ser encerrado.

---

### 📜 Módulo de Contratos

#### **US06 - Assinatura de Contrato**
* **Como:** um cliente com pedido aprovado,
* **Eu quero:** visualizar os termos do contrato e registrar minha assinatura digital,
* **Para que:** o aluguel seja formalizado e o veículo reservado.

---

### 🏢 Módulo de Gestão e Parceria (Banco e Empresa)

#### **US07 - Cadastro de Automóveis (Empresa)**
* **Como:** um funcionário da Empresa de Aluguel,
* **Eu quero:** cadastrar novos veículos informando placa, modelo, ano e valor da diária,
* **Para que:** eles fiquem disponíveis para reserva pelos clientes.
* **Critério de Aceite:** Validação de unicidade da placa e ano de fabricação compatível com as regras de negócio.

#### **US08 - Análise de Crédito Detalhada (Banco)**
* **Como:** um analista do Banco,
* **Eu quero:** acessar o histórico de rendimentos do cliente vinculado a um pedido,
* **Para que:** eu possa emitir um parecer de aprovação financeira baseado no limite de crédito.
* **Critério de Aceite:** O Banco possui visão exclusiva dos dados financeiros, sem permissão para edição.

#### **US09 - Consulta de Pedidos Pendentes (Empresa)**
* **Como:** um gestor da Empresa de Aluguel,
* **Eu quero:** visualizar quais pedidos já foram aprovados pelo Banco,
* **Para que:** eu possa preparar a entrega do veículo e gerar o contrato final.
* **Critério de Aceite:** Filtro funcional por status "Aprovado pelo Banco".

---

### 🛠️ Módulo de Operações e Manutenção

#### **US10 - Atualização de Disponibilidade (Empresa)**
* **Como:** um funcionário da Empresa de Aluguel,
* **Eu quero:** alterar o status de um veículo (Ex: Em Manutenção, Disponível, Alugado),
* **Para que:** os clientes não consigam reservar carros indisponíveis.
* **Critério de Aceite:** Veículos em manutenção devem ser ocultados automaticamente da busca.

#### **US11 - Registro de Devolução e Check-out**
* **Como:** um Agente da Empresa,
* **Eu quero:** registrar a devolução do veículo e encerrar o contrato,
* **Para que:** o automóvel volte ao estoque disponível e o histórico seja atualizado.
* **Critério de Aceite:** Cálculo automático de multas por atraso com base na data prevista.

---

### 🛡️ Módulo de Segurança e Infraestrutura (Técnica)

#### **US12 - Recuperação de Acesso e Auditoria**
* **Como:** um usuário (Cliente ou Agente),
* **Eu quero:** solicitar a recuperação de senha via e-mail,
* **Para que:** eu possa recuperar o acesso à minha conta com segurança.
* **Critério de Aceite:** Uso de Token com expiração e registro de logs de auditoria.
