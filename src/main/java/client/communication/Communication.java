package client.communication;

import client.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {

    private RestTemplate restTemplate;
    private List<String> cookies;
    private HttpHeaders headers = new HttpHeaders();
    private final String URL ="http://94.198.50.185:7081/api/users";

    @Autowired
    public Communication (RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        List<User> allUsers = responseEntity.getBody();
        cookies = responseEntity.getHeaders().get("Set-Cookie");
        headers.set("Cookie", cookies.stream().collect(Collectors.joining(";")));
        return allUsers;
    }

    public String addUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        return responseEntity.getBody();
    };

    public String editUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        return responseEntity.getBody();
    }

    public String deleteUser(Long id) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        return responseEntity.getBody();
    }
}
