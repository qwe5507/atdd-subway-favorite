package nextstep.favorite.application;

import nextstep.favorite.application.dto.FavoriteRequest;
import nextstep.favorite.application.dto.FavoriteResponse;
import nextstep.favorite.domain.Favorite;
import nextstep.favorite.domain.FavoriteRepository;
import nextstep.member.application.MemberService;
import nextstep.member.domain.LoginMember;
import nextstep.member.domain.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private FavoriteRepository favoriteRepository;
    private MemberService memberService;

    public FavoriteService(FavoriteRepository favoriteRepository, final MemberService memberService) {
        this.favoriteRepository = favoriteRepository;
        this.memberService = memberService;
    }

    /**
     * TODO: LoginMember 를 추가로 받아서 FavoriteRequest 내용과 함께 Favorite 를 생성합니다.
     *
     * @param loginMember
     * @param request
     */
    public void createFavorite(final LoginMember loginMember, FavoriteRequest request) {
        final Member member = memberService.findMemberByEmail(loginMember.getEmail());

        Favorite favorite = new Favorite();
        favoriteRepository.save(favorite);
    }

    /**
     * TODO: StationResponse 를 응답하는 FavoriteResponse 로 변환해야 합니다.
     *
     * @return
     */
    public List<FavoriteResponse> findFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        return null;
    }

    /**
     * TODO: 요구사항 설명에 맞게 수정합니다.
     * @param id
     */
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }
}
