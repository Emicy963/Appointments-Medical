# Sistema de Agendamento de Consultas Médicas

Sistema desktop desenvolvido em Java para gerenciamento de consultas médicas, permitindo que usuários realizem agendamentos com diferentes profissionais da saúde.

## Funcionalidades

- Sistema de autenticação (Login/Registro)
- Agendamento de consultas médicas
- Visualização de consultas agendadas
- Edição de consultas existentes
- Cancelamento de consultas
- Suporte a diferentes tipos de consultas e profissionais

### Novas funcionalidades:

- Armazenar as informações em um Banco de Dados
- Suporta fotos de perfil para cada usuário
- Sistema de pesquisa de consulta
- Gerar relatórios de consulta

## Estrutura do Projeto

```
src/
├── view/
│   ├── Login.java         # Tela de login
│   ├── Registrar.java     # Tela de registro de usuários
│   └── TelaUsuario.java   # Tela principal do sistema
├── model/
│   ├── Usuario.java       # Modelo de usuário
│   └── Appointment.java   # Modelo de consulta
└── controller/
    ├── UsuarioController.java    # Controle de usuários
    ├── DataBaseConnection.java   # Controle de conexão com Base de Dados(criar caso de clonagem)
    └── AppointmentController.java # Controle de consultas
```

## Tecnologias Utilizadas

- Java
- Swing (Interface gráfica)
- NetBeans IDE
- MySQL Workbeanch

## Pré-requisitos

- JDK 8 ou superior
- NetBeans IDE (recomendado)
- MySQL (ou qualquer outro banco de dados relacional)

## Como Executar

1. Clone este repositório
2. Abra o projeto no NetBeans IDE
3. Configure a conexão com o banco de dados em `src/config/database.properties` (se aplicável)
4. Execute a classe `src/AgendamentoConsulta/AgendamentoConsulta.java`

## Funcionalidades por Tela

### Tela de Login

- Autenticação de usuários
- Redirecionamento para registro de novos usuários

### Tela de Registro

- Cadastro de novos usuários
- Validação de dados
- Redirecionamento para login

### Tela Principal (TelaUsuario)

- Agendamento de novas consultas
- Visualização de consultas existentes
- Edição de consultas
- Cancelamento de consultas
- Logout do sistema

## Tipos de Consultas Disponíveis

- Geral
- Dentista
- Oftalmologia
- Análise
- Outras

## Profissionais Disponíveis

- Dr. Raúl
- Dra. Carla
- Dra. Paula
- Dr. Antonio

## Autor

Anderson Cafurica

## Licença

Este projeto está sob a licença [MIT License

Copyright (c) 2024 Anderson Cafurica

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.]
