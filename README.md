# Monitor de Métricas de Saúde e Composição Corporal (Android)

Esta aplicação consiste em uma solução nativa Android, desenvolvida utilizando o toolkit Jetpack Compose, voltada para o cálculo, monitoramento e persistência de dados antropométricos. O sistema implementa fórmulas consolidadas na literatura científica para fornecer estimativas de Índice de Massa Corporal (IMC), Taxa Metabólica Basal (TMB) e percentual de gordura corporal.

---

## 📋 Visão Geral do Projeto

O objetivo do software é fornecer uma interface intuitiva e responsiva para o acompanhamento da saúde do usuário. A arquitetura foi projetada priorizando a modularidade, a segurança no manuseio de arquivos e a eficiência no armazenamento local de dados.

### Interface de Usuário (UI)
O design system adota os princípios do Material Design 3, caracterizando-se por:
 Painel de Controle (Dashboard): Apresentação hierárquica das informações, com destaque visual para os indicadores críticos (IMC) e cartões informativos para métricas secundárias.
 Visualização de Dados: Implementação de gráficos lineares para análise temporal da evolução do usuário.
 Componentes: Utilização de elementos de interface modernos, incluindo botões com design arredondado e layouts de cartões com elevação para separação de contexto.

---

## ⚙️ Funcionalidades Técnicas

### 1. Algoritmos de Cálculo
O núcleo lógico da aplicação implementa equações matemáticas específicas para cada indicador:
 IMC (Índice de Massa Corporal): Cálculo padrão conforme diretrizes da Organização Mundial da Saúde (OMS).
 TMB (Taxa Metabólica Basal): Utilização da equação de Mifflin-St Jeor, reconhecida por sua precisão em indivíduos modernos.
 Gordura Corporal: Implementação do Método da Marinha Americana (US Navy Method), que utiliza medidas de circunferência (pescoço, cintura, quadril) e estatura.

### 2. Persistência de Dados
O armazenamento local é gerenciado pela biblioteca Room Persistence Library, uma camada de abstração sobre o SQLite.
 Entidade: `IMCResultEntity` armazena o estado completo de cada medição.
 DAO (Data Access Object): Gerencia as transações de banco de dados de forma assíncrona.

### 3. Interoperabilidade e Exportação
O sistema permite a exportação do histórico de registros para o formato `.csv`.
 Segurança: A geração do arquivo ocorre no diretório de cache interno da aplicação.
 Compartilhamento: Utiliza-se a API `FileProvider` para conceder permissões temporárias de leitura a aplicativos externos (clientes de e-mail, planilhas), garantindo conformidade com as políticas de armazenamento do Android (Scoped Storage).

### 4. Agendamento de Notificações
Implementação de lembretes diários para engajamento do usuário, utilizando `AlarmManager` e `BroadcastReceiver`, com verificação de permissões em tempo de execução para compatibilidade com Android 13+.

---

## 🏗 Arquitetura de Software

O projeto está estruturado com base em funcionalidades (Feature-based structure), promovendo o desacoplamento e facilitando a manutenção.

```text
com.example.calculadoraimc
├── database/          # Camada de Persistência (Room, Entidades e DAOs)
├── datasource/        # Lógica de Negócios e Utilitários (Exportadores, Receivers)
├── feature/           # Módulos de Funcionalidade
│   ├── home/          # Lógica e UI do Painel Principal
│   └── history/       # Lógica e UI de Histórico e Análise Gráfica
├── ui/
│   ├── theme/         # Definições de Estilo e Temas
│   └── MainActivity.kt # Ponto de Entrada e Orquestração de Navegação