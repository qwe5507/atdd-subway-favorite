package nextstep.member.application;

import nextstep.member.application.dto.GithubProfileResponse;
import nextstep.member.application.dto.TokenResponse;
import nextstep.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class GithubService {

    private final GithubClient githubClient;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    public GithubService(final GithubClient githubClient, final MemberService memberService, final JwtTokenProvider jwtTokenProvider) {
        this.githubClient = githubClient;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createToken(final String code) {
        final String githubToken = githubClient.requestGithubToken(code);
        final GithubProfileResponse githubProfileResponse = githubClient.requestGithubProfile(githubToken);

        final String email = githubProfileResponse.getEmail();
//        final Member member = memberService.findMemberByEmail(email);

        String token = jwtTokenProvider.createToken(email);

        return new TokenResponse(token);
    }
}
