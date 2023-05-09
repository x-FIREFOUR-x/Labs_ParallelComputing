import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class CommonWordCounter {
    private final ForkJoinPool forkJoinPool;

    public CommonWordCounter(int countThreads) {
        forkJoinPool = new ForkJoinPool(countThreads);
    }

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public HashSet<String> findCommonWordsParallel(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }

    public static HashSet<String> getUniqueWordsInDocument(Document document) {
        HashSet<String> uniqueWords = new HashSet<>();
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                uniqueWords.add(word.toLowerCase());
            }
        }
        return uniqueWords;
    }

}
