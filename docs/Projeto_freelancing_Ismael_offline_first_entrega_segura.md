# Projeto_freelancing_Ismael.md
Versão totalmente reestruturada para Kotlin Android Nativo com Jetpack Compose.

## 1. Visão Geral do Produto
O aplicativo tem como objetivo organizar e registrar entregas de Equipamentos de Proteção Individual (EPI) em ambientes rurais. O processo atual é manual. O MVP digitaliza o fluxo de entrega, incluindo registro, validação, notificações e rastreabilidade total.

## 2. Objetivo do MVP
Criar um aplicativo Android com arquitetura **offline-first**, permitindo operação total sem internet, mas com sincronização automática com backend sempre que houver conexão.  
Principais recursos:
- Registro de funcionários, EPIs e entregas.
- Confirmação biométrica (BiometricPrompt (gerente)).
- Foto do EPI (CameraX).
- Persistência local (Room).
- Sincronização periódica com backend (WorkManager).
- Interface Jetpack Compose.
- Navegação com Navigation Compose.
- Notificações usando WorkManager.
- Geração de PDF local usando PdfDocument.
- Exportação de CSV.
- Auditoria local e sincronizada.

## 3. Stack Técnica
- Linguagem: Kotlin
- UI: Jetpack Compose
- Persistência: Room (SQLite)
- Arquitetura: MVVM + UseCases + Repositories
- Navegação: Navigation Compose
- Sincronização: WorkManager + API REST
- Fotos: CameraX
- Biometria: androidx.biometric
- Notificações: WorkManager + NotificationCompat
- PDF: PdfDocument
- Exportação: Kotlin CSV + SAF
- Armazenamento: File API
- Estado: StateFlow, ViewModel

## 4. Arquitetura (MVVM)
Camadas:
- domain: entidades e casos de uso.
- data: DAOs, entities, DB, repositórios.
- ui: telas Compose, ViewModels, eventos e estados.
- core: utilidades, infraestrutura, extensões.

Fluxo de dados:
Compose → ViewModel → UseCase → Repository → Room  
Room → Flow → ViewModel → Compose  
Room (dados pendentes) → WorkManager → Backend → Resposta → Room (sincronizado)

## 5. Modelo de Dados
employees:
- id, name, sector, role, shoe_size, uniform_size, active, synced, updated_at

epi_types:
- id, name, months_validity, synced, updated_at

deliveries:
- id, employee_id, epi_type_id, delivered_at, due_at, confirm_method, photo_path, synced, updated_at

audit_log:
- id, action, entity, entity_id, timestamp, payload, synced

## 6. Fluxos Funcionais

### Fluxo: Registrar entrega segura
1. Selecionar funcionário.
2. Escolher EPI.
3. Calcular due_at.
4. Validar biometria do gerente.
5. Foto opcional.
6. Registrar no Room com `synced = false`.
7. Agendar notificação.
8. Gerar PDF local.
9. WorkManager tenta sincronizar quando houver internet.

### Fluxo: Sincronização offline-first
1. WorkManager verifica conectividade.
2. Consulta records com `synced = false`.
3. Envia lote para o backend.
4. Backend salva e retorna ids definitivos.
5. App atualiza registros como `synced = true`.

### Fluxo: Exportar CSV
1. Selecionar intervalo.
2. Consultar Room.
3. Gerar CSV.
4. Compartilhar.

## 7. Guia Técnico

### Criação do projeto
- Android Studio → New Project → Empty Compose Activity.
- Kotlin.
- Min SDK 24.

### Dependências essenciais
- Room
- Lifecycle (ViewModel, LiveData/Flow)
- Navigation Compose
- Biometric
- CameraX
- WorkManager (sincronização, notificações)
- Retrofit/OkHttp para sincronização

### Jetpack Compose
- Estrutura: screens/, components/, theme/

### Room
- Entities com flags `synced`, `updated_at`
- Migrations

### Sincronização
- Implementada com WorkManager.
- Responsável por envio confiável e idempotente ao backend.
- Backend faz controle e evita duplicações usando UUID local.

### PDF
- PdfDocument para recibo.

## 8. Milestones

### M0 — Base
- Setup Kotlin Compose.
- Room configurado.
- CRUD básico.
- Flags `synced` e timestamps.

### M1 — Fluxo de entrega
- Biometria.
- Foto.
- Notificações.
- Registro completo local.

### M2 — Sincronização
- WorkManager enviando dados.
- Backend simples recebendo alterações.
- Controle de sincronização robusto.

### M3 — Relatórios e Exportações
- PDF.
- Export CSV.

### M4 — Polimento
- Auditoria.
- Indicador de status de sincronização.
- Testes manuais.

## 9. Diretrizes para LLM
- Código Kotlin idiomático.
- Seguir MVVM.
- Modularidade.
- Explicar trade-offs.
- Respeitar arquitetura offline-first.
- Evitar bibliotecas desnecessárias.

## 10. CONTRIBUTING

Padrão de branches:
- feature/
- fix/
- chore/
- docs/

Commits no formato Conventional Commits:
- feat(scope): descrição
- fix(scope): descrição

PR deve conter:
- Descrição.
- Como testar.
- Prints.
- Riscos.
- Checklist.

## 11. FAQ

Por que offline-first?
- Porque fazendas têm internet instável, mas exigem confiabilidade total.  
- Operação funciona 100% local sem depender de rede.  
- Segurança e backup são garantidos via sincronização quando possível.

O app precisa de internet?
- Para operar: **não**.  
- Para sincronizar e garantir segurança dos dados: **sim, quando disponível**.

Backend será obrigatório no futuro?
- Sim, para garantir segurança, auditoria e histórico centralizado.  
- O MVP prevê backend mínimo e escalável.

## 12. Evidência de Entrega e Segurança Antifraude

A confirmação da entrega seguirá três etapas obrigatórias:

1. **Assinatura digital do funcionário**  
   - O funcionário assina com o dedo na tela, reconhecendo o recebimento do EPI.
   - A assinatura é armazenada como imagem vinculada ao registro da entrega.

2. **Foto do EPI (somente o item)**  
   - O gerente tira uma foto do EPI entregue, sem mostrar funcionários.
   - A imagem serve como registro visual do item entregue e seu estado no momento.

3. **Biometria do gerente**  
   - Antes de finalizar a entrega, o aplicativo solicita a biometria do gerente via BiometricPrompt.
   - Garante que somente usuários autorizados realizem entregas.

Esse conjunto cria um fluxo equilibrado entre segurança, privacidade e usabilidade.
