package soomin.park.sajamart.api.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static soomin.park.sajamart.api.item.ItemStatus.SELL;

@SpringBootTest // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
@AutoConfigureRestDocs
class ItemControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역직렬화를 위한 클래스

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void setMockMvc() {
        itemRepository.deleteAll();
    }

    @DisplayName("addItem: 상품 추가 성공")
    @Test
    public void addItem() throws Exception {

        // given
        final ItemRequest request = ItemRequest.builder()
                .title("젤다의 전설 티어스 오브 더 킹덤")
                .price(50000)
                .availableStock(9999)
                .detail("3인칭 오픈 에어 액션 어드벤처")
                .itemStatus(SELL)
                .build();

        final String requestBody = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/items")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(HAL_JSON)
                        .header("Authorization", "Bearer " + getAccessToken())
                        .content(requestBody))
                .andDo(print());

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists()) // json 경로에 id가 있는지
                .andExpect(header().exists(LOCATION)) // Headers 에 Location 이 있는지
                .andDo(document("items",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("itemList").description("상품 목록"),
                                linkWithRel("profile").description("사용 가이드")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(CONTENT_TYPE).description("content type header")
                        ),
                        requestFields(
                                fieldWithPath("title").description("상품명"),
                                fieldWithPath("price").description("가격"),
                                fieldWithPath("availableStock").description("재고"),
                                fieldWithPath("detail").description("상세설명"),
                                fieldWithPath("itemStatus").description("상품상태")
                        ),
                        responseHeaders(
                                headerWithName(LOCATION).description("Location header"),
                                headerWithName(CONTENT_TYPE).description("Content type")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("id").description("상품ID"),
                                fieldWithPath("title").description("상품명"),
                                fieldWithPath("price").description("가격"),
                                fieldWithPath("availableStock").description("재고"),
                                fieldWithPath("detail").description("상세설명"),
                                fieldWithPath("itemStatus").description("상품상태"),
                                fieldWithPath("createAt").description("등록일"),
                                fieldWithPath("updateAt").description("수정일")
                        )
                ));
    }

    // 인증 토큰 가져오기
    private String getAccessToken() throws Exception {
        String grantType = "password";
        String clientId = "sajamart";
        String scope = "sajamart:read";
        String username = "john";
        String password = "password";

        String url = "http://localhost:9999/realms/master/protocol/openid-connect/token";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("grant_type", grantType);
        parameters.add("client_id", clientId);
        parameters.add("scope", scope);
        parameters.add("username", username);
        parameters.add("password", password);

        var httpEntity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
        var responseEntity = new TestRestTemplate().postForEntity(url, httpEntity, HashMap.class);

        return responseEntity.getBody().get("access_token").toString();
    }
}