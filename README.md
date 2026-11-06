<br/>
<p align="center">
  <!-- Substitua a URL abaixo pela imagem da sua logo -->
  <a href="https://github.com/atlas-software-dev/cipo-flow-kmm">
    <img src="https://via.placeholder.com/200x200.png?text=CIPO+FLOW+LOGO" alt="Cipo Flow KMM Logo" width="200"/>
  </a>
</p>

<h1 align="center">Cipo Flow KMM</h1>

<p align="center">
  <strong>Uma biblioteca Kotlin Multiplatform Mobile (KMM) para gerenciamento eficiente de fluxos de dados e estados entre Android e iOS.</strong>
</p>

<p align="center">
  <a href="https://github.com/atlas-software-dev/cipo-flow-kmm/actions"><img src="https://img.shields.io/github/actions/workflow/status/atlas-software-dev/cipo-flow-kmm/build.yml?branch=main&style=flat-square" alt="Build Status"></a>
  <a href="https://maven-badges.herokuapp.com/maven-central/dev.atlas-software/cipo-flow-kmm"><img src="https://img.shields.io/maven-central/v/dev.atlas-software/cipo-flow-kmm.svg?style=flat-square" alt="Maven Central"></a>
  <a href="https://kotlinlang.org/docs/multiplatform.html"><img src="https://img.shields.io/badge/Kotlin-Multiplatform-blue.svg?style=flat-square&logo=kotlin" alt="Kotlin Multiplatform"></a>
  <a href="https://github.com/atlas-software-dev/cipo-flow-kmm/blob/main/LICENSE"><img src="https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square" alt="License"></a>
</p>

<hr/>

## 📖 Tabela de Conteúdos

- [Sobre o Projeto](#-sobre-o-projeto)
- [Principais Funcionalidades](#-principais-funcionalidades)
- [Instalação](#-instalação)
  - [Android / Kotlin Multiplatform](#android--kotlin-multiplatform)
  - [iOS](#ios)
- [Como Usar](#-como-usar)
  - [Exemplo Compartilhado (KMM)](#exemplo-compartilhado-kmm)
  - [Exemplo no Android](#exemplo-no-android)
  - [Exemplo no iOS](#exemplo-no-ios)
- [Arquitetura](#-arquitetura)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)
- [Contato](#-contato)

## 💡 Sobre o Projeto

O **Cipo Flow KMM** foi desenvolvido pela equipe [Atlas Software](https://github.com/atlas-software-dev) para resolver o desafio de manter fluxos de dados complexos consistentes entre plataformas móveis. 

Escrever lógica de estado repetitiva para Android e iOS é propenso a erros. Com o Cipo Flow, você escreve o comportamento do seu fluxo (Cipo) apenas uma vez no módulo compartilhado (KMM) e consome de forma nativa e reativa no Android (via `StateFlow`/`Jetpack Compose`) e no iOS (via `Combine`/`SwiftUI`).

## ✨ Principais Funcionalidades

- 🌍 **100% Kotlin Multiplatform**: Partilhe a lógica de negócio principal perfeitamente entre iOS e Android.
- ⚡ **Reativo por Padrão**: Construído sobre o robusto `kotlinx.coroutines` e `Flow`.
- 🍎 **Interoperação com Swift (iOS)**: Encapsulamento amigável para `Combine` e concorrência estruturada nativa do Swift (`async`/`await`).
- 🛠️ **Leve e Modular**: Zero dependências pesadas, focado apenas no transporte eficiente de estados.
- 🛡️ **Seguro contra Erros**: Tratamento robusto para concorrência e vazamento de memória nas transições de ciclo de vida.

## 📦 Instalação

### Android / Kotlin Multiplatform

Adicione a dependência no `build.gradle.kts` do seu módulo KMM (`shared`) ou módulo Android:

```kotlin
// build.gradle.kts
sourceSets {
    val commonMain by getting {
        dependencies {
            implementation("dev.atlas-software:cipo-flow-kmm:1.0.0") // Substitua pela versão atual
        }
    }
}
```

### iOS

Se você estiver distribuindo o framework nativamente (XCFramework), o Cipo Flow KMM já estará incluído no binário partilhado.
Caso utilize o Swift Package Manager (SPM) ou CocoaPods com o seu módulo KMM, nenhuma etapa extra de dependência é necessária além da configuração padrão do KMM.

## 🚀 Como Usar

### Exemplo Compartilhado (KMM)

Crie os seus casos de uso ou viewmodels na camada compartilhada (`shared/src/commonMain`):

```kotlin
import dev.atlas.cipoflow.CipoFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MeuCipoViewModel {
    private val _estado = MutableStateFlow(EstadoDaTela.Carregando)
    
    // O CipoFlow encapsula o Flow padrão para garantir consumo seguro no iOS
    val estado = CipoFlow(_estado.asStateFlow())

    fun carregarDados() {
        // Lógica multiplataforma...
        _estado.value = EstadoDaTela.Sucesso("Dados carregados com sucesso!")
    }
}

sealed class EstadoDaTela {
    object Carregando : EstadoDaTela()
    data class Sucesso(val mensagem: String) : EstadoDaTela()
}
```

### Exemplo no Android

No Android, consome-se a biblioteca usando as funções nativas do Jetpack Compose de forma fluída:

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun MinhaTela(viewModel: MeuCipoViewModel = viewModel()) {
    // Collecta o CipoFlow diretamente
    val estado by viewModel.estado.collectAsState()

    when (val state = estado) {
        is EstadoDaTela.Carregando -> LoadingView()
        is EstadoDaTela.Sucesso -> TextView(text = state.mensagem)
    }
}
```

### Exemplo no iOS

No iOS, o `CipoFlow` traduz o fluxo para que o Swift possa observá-lo facilmente usando `@Published` e `Combine`, ou concorrência nativa.

```swift
import SwiftUI
import SharedModule // O nome do seu módulo KMM gerado

class MeuViewModelIos: ObservableObject {
    private let kmmViewModel = MeuCipoViewModel()
    @Published var mensagem: String = "Carregando..."
    
    init() {
        // Observar mudanças vindas do KMM
        kmmViewModel.estado.collect(onEmit: { novoEstado in
            if let sucesso = novoEstado as? EstadoDaTela.Sucesso {
                DispatchQueue.main.async {
                    self.mensagem = sucesso.mensagem
                }
            }
        })
    }
}

struct MinhaView: View {
    @StateObject var viewModel = MeuViewModelIos()

    var body: some View {
        Text(viewModel.mensagem)
    }
}
```

## 🏗️ Arquitetura

O **Cipo Flow KMM** atua como uma ponte (bridge) arquitetural. O Kotlin Flow puro muitas vezes possui desafios no momento de ser consumido diretamente via Objective-C/Swift. A nossa biblioteca lida com os detalhes de `Dispatcher`, cancelamento de `Jobs` e retém tipos genéricos, deixando as interfaces Swift muito mais limpas.

## 🤝 Contribuindo

Nós adoramos contribuições! Para fazer parte, siga estas etapas:

1. Faça um Fork do projeto.
2. Crie uma Branch para a sua Feature (`git checkout -b feature/IncrívelFeature`).
3. Adicione as suas mudanças e faça os Commits (`git commit -m 'Adiciona alguma IncrívelFeature'`).
4. Faça o Push para a Branch (`git push origin feature/IncrívelFeature`).
5. Abra um **Pull Request**.

Certifique-se de ler nossas [Diretrizes de Contribuição](CONTRIBUTING.md) antes de submeter PRs.

## 📄 Licença

Distribuído sob a licença **Apache License 2.0**. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## ✉️ Contato

**Atlas Software Dev**
- Organização GitHub: [@atlas-software-dev](https://github.com/atlas-software-dev)
- Issue Tracker: [GitHub Issues](https://github.com/atlas-software-dev/cipo-flow-kmm/issues)

---
<p align="center">
  <i>Feito com ❤️ pela equipe Atlas Software</i>
</p>
