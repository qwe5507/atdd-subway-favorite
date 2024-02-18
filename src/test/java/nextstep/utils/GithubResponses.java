package nextstep.utils;

public enum GithubResponses {
    사용자1("aofijeowifjaoief", "access_token_1", "email1@email.com"), 사용자2("fau3nfin93dmn", "access_token_2", "email2@email.com"), 사용자3("afnm93fmdodf", "access_token_3", "email3@email.com"), 사용자4("fm04fndkaladmd", "access_token_4", "email4@email.com");

    private final String code;
    private final String accessToken;
    private final String email;

    GithubResponses(final String code, final String accessToken, final String email) {
        this.code = code;
        this.accessToken = accessToken;
        this.email = email;
    }

    public String code() {
        return code;
    }

    public String accessToken() {
        return accessToken;
    }
}
