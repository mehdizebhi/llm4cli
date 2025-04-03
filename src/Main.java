///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.6.3
//DEPS dev.langchain4j:langchain4j-google-ai-gemini:1.0.0-beta2
//DEPS dev.langchain4j:langchain4j:1.0.0-beta2
//DEPS org.apache.logging.log4j:log4j-api:2.24.3
//DEPS org.apache.logging.log4j:log4j-core:2.24.3
//DEPS org.apache.logging.log4j:log4j-slf4j2-impl:2.24.3

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel;
import static dev.langchain4j.model.LambdaStreamingResponseHandler.onPartialResponse;

@Command(name = "Llm4cli", mixinStandardHelpOptions = true, version = "Llm4cli 0.0.2", description = "LLM Command line tool for chatting with various models in terminal.")
class Main implements Callable<Integer> {

    @Option(names = {"--prompt", "-p"}, required = true, description = "The user prompt")
    private String prompt;

    @Option(names = {"--vendor","-v"}, required = false, description = "Model provider name", defaultValue = "google")
    private String vendorName;

    @Option(names = {"--model","-m"}, required = false, description = "The LLM model to use", defaultValue = "gemini-2.0-flash")
    private String modelName;

    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        List<String> models = availableModels().getOrDefault(vendorName, List.of());
        if (models.isEmpty()) {
            System.err.println(
                    "Invalid vendor name: " + vendorName + ". Available vendors: " + availableModels().keySet());
            return 1;

        } else if (!models.contains(modelName)) {
            System.err.println("Invalid model name: " + modelName + ". Available models: " + models);
            return 1;
        }

        StreamingChatLanguageModel model = switch (vendorName) {
            case "google" -> gemini(modelName);
            case "anthropic" -> {
                // Add logic for Anthropic models here
                System.err.println("Anthropic models are not yet implemented.");
                yield null;
            }
            case "openai" -> {
                // Add logic for OpenAI models here
                System.err.println("OpenAI models are not yet implemented.");
                yield null;
            }
            default -> {
                System.err.println("Unsupported vendor: " + vendorName);
                yield null;
            }
        };

        if (model == null) {
            return 1;
        }

        model.chat(prompt, onPartialResponse(System.out::print));

        return 0;
    }

    // ------------------------------
    // Private Helper
    // ------------------------------

    private GoogleAiGeminiStreamingChatModel gemini(String modelName) {
        return GoogleAiGeminiStreamingChatModel.builder()
                .modelName(modelName)
                .apiKey(System.getenv("GEMINI_API_KEY"))
                .build();
    }

    private Map<String, List<String>> availableModels() {
        return Map.of(
                "google", geminiAvailableModels(),
                "anthropic", anthropicAvailableModels(),
                "openai", openAiAvailableModels());
    }

    private List<String> geminiAvailableModels() {
        return List.of(
                "gemini-1.0-pro",
                "gemini-1.5-pro",
                "gemini-1.5-flash",
                "gemini-2.0-flash");
    }

    private List<String> anthropicAvailableModels() {
        return List.of(
                "CLAUDE_3_5_SONNET_20240620");
    }

    private List<String> openAiAvailableModels() {
        return List.of(
                "gpt-4o");
    }
}