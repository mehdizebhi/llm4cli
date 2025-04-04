# Llm4cli - LLM Command Line Interface

`llm4cli` is a powerful command-line tool that allows you to interact with various Large Language Models (LLMs) directly from your terminal. It currently supports Google's Gemini models, with plans to integrate more vendors like OpenAI and Anthropic in future versions.

## Features

- Chat with LLMs directly from the command line
- Supports multiple models from different providers
- Lightweight and easy to use
- No need for complex setups—just run and chat!

## Installation

### 1. Download Prebuilt Executable (Recommended)

For a quick and easy setup, download the latest release from the [GitHub Releases](https://github.com/mehdizebhi/llm4cli/releases) page based on your operating system.

#### Windows
1. Download the `llm.exe` file from the releases page.
2. Move the file to a directory like `C:\tools\llm4cli`.
3. Add this directory to your system's PATH variable.
4. Open a new terminal and test:
   ```bash
   llm -p "Tell me a joke."
   ```

#### Linux / macOS
1. Download the `llm` binary from the releases page.
2. Move it to `/usr/local/bin/` and make it executable:
   ```bash
   chmod +x llm
   sudo mv llm /usr/local/bin/
   ```
3. Verify installation:
   ```bash
   llm -p "Tell me a joke."
   ```

### 2. Build from Source (Alternative)

If you prefer to build from source, follow these steps:

#### Install JBang (if not already installed)
`llm4cli` is built using [JBang](https://www.jbang.dev/), which allows Java scripts to run seamlessly without additional setup. You can install JBang using the following instructions:

[Install JBang](https://www.jbang.dev/download/)

#### Run `llm4cli`
Once JBang is installed, you can directly run `llm4cli` using:

```bash
jbang --fresh https://github.com/mehdizebhi/llm4cli/blob/main/src/Main.java -p "Hello!"
```

Alternatively, you can clone the repository and run it locally:

```bash
git clone https://github.com/mehdizebhi/llm4cli.git
cd llm4cli/src
jbang Main.java -p "Hello!"
```

You can also install `llm4cli` into your system path using jbang without compiling the Java file:

```bash
git clone https://github.com/mehdizebhi/llm4cli.git
cd llm4cli
jbang app install --name llm .\src\Main.java
```

#### Create a Native Executable with GraalVM
To build a standalone executable, follow these steps:

1. Export the JAR file with dependencies:
   ```bash
   jbang export portable Main.java
   ```
2. Build the native executable using GraalVM:
   ```bash
   native-image -cp .\lib\picocli-4.6.3.jar -H:ReflectionConfigurationFiles=reflect-config.json -jar .\Main.jar llm
   ```

## Usage

You can use `llm4cli` by providing different arguments to specify the LLM provider and model. Below are some examples:

### Basic Usage
```bash
llm -p "What is the meaning of life?"
```

### Specify a Model Vendor
```bash
llm -p "Tell me a joke." -v google
```

### Choose a Specific Model
```bash
llm -p "Explain how AI works." -v google -m gemini-2.0-flash
```

## Supported Models

Currently, `llm4cli` supports the following models:

### Google Gemini

- `gemini-1.0-pro`
- `gemini-1.5-pro`
- `gemini-1.5-flash`
- `gemini-2.0-flash`

More models from OpenAI and Anthropic will be added soon.

## Setting Up Environment Variables

Before using `llm4cli`, you need to set up the API key for Gemini models. Set the environment variable as follows:

```bash
export GEMINI_API_KEY="your_api_key"
```

On Windows (PowerShell):

```powershell
$env:GEMINI_API_KEY = "your_api_key"
```

You can obtain an API key from [Google AI API Key Registration](https://ai.google.dev/gemini-api/docs/api-key).

## Contributing

We welcome contributions! Feel free to submit issues or pull requests to improve `llm4cli`.

## License

`llm4cli` is released under the MIT License.

---

Happy chatting! 🚀
