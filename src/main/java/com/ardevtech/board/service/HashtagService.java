package com.ardevtech.board.service;

import com.ardevtech.board.domain.Hashtag;
import com.ardevtech.board.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    @Transactional(readOnly = true)
    public Set<Hashtag> findHashtagsByNames(Set<String> hashtagNames) {
        return new HashSet<>(hashtagRepository.findByHashtagNameIn(hashtagNames));
    }

    public Set<String> parseHashtagNames(String content) {
        if (content == null) {
            return Set.of();
        }

        Pattern pattern = Pattern.compile("#[\\w가-힣]+");
        Matcher matcher = pattern.matcher(content.strip()); // 앞 뒤 공백 문자 제거
        Set<String> result = new HashSet<>();

        while (matcher.find()) { // 정규식에 맞는 문자가 발견되면, # 을 제
            result.add(matcher.group().replace("#", ""));
        }
        return Set.copyOf(result); // 불변객체
    }

    public void deleteHashtagWithoutArticles(Long hashtagId) {
        // 게시글이 지워졌을 때 관련 해시태그가 지워지면 안되니, 게시글이 없으면 삭제 가능하게 변경
        Hashtag hashtag = hashtagRepository.getReferenceById(hashtagId);
        if (hashtag.getArticles().isEmpty()) {
            hashtagRepository.delete(hashtag);
        }
    }
}
