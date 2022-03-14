package example;

import example.person.Person;
import example.person.PersonRepository;
import example.weather.WeatherResponse;
import example.weather.WeatherClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ExampleControllerTest {

    @InjectMocks
    private ExampleController subject;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private WeatherClient weatherClient;


    @Before
    public void setUp() throws Exception {
        subject = new ExampleController(personRepository, weatherClient);
    }

    @Test
    public void shouldReturnHelloWorld() throws Exception {
        assertThat(subject.hello(), is("Hello World!"));
    }

    @Test
    public void shouldReturnFullNameOfAPerson() throws Exception {
        Person peter = new Person("Peter", "Pan");
        given(personRepository.findByLastName("Pan")).willReturn(Optional.of(peter));

        var greeting = subject.hello("Pan");

        assertThat(greeting, is("Hello Peter Pan!"));
    }

    @Test
    public void shouldTellIfPersonIsUnknown() throws Exception {
        given(personRepository.findByLastName(anyString())).willReturn(Optional.empty());

        var greeting = subject.hello("Pan");

        assertThat(greeting, is("Who is this 'Pan' you're talking about?"));
    }

    //INSERT_TESTS_HERE
    
}
