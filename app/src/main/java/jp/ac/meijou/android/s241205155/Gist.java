package jp.ac.meijou.android.s241205155;

import java.util.Map;

public class Gist {
    public Map<String, GistFile> files;

    public static class GistFile {
        public String content;
    }
}