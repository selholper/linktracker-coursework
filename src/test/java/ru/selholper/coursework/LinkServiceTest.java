package ru.selholper.coursework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.selholper.coursework.models.Link;
import ru.selholper.coursework.repositories.LinkRepository;
import ru.selholper.coursework.services.LinkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LinkServiceTest {

    @Mock
    private LinkRepository linkRepository;

    @InjectMocks
    private LinkService linkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        int id = 1;
        Link link = new Link();
        link.setId(id);
        when(linkRepository.findById(id)).thenReturn(Optional.of(link));

        Optional<Link> result = linkService.findById(id);

        assertEquals(id, result.get().getId());
    }

    @Test
    public void testGetById() {
        int id = 1;
        Link link = new Link();
        link.setId(id);
        when(linkRepository.getById(id)).thenReturn(link);

        Link result = linkService.getById(id);

        assertEquals(id, result.getId());
    }

    @Test
    public void testDeleteById() {
        int id = 1;

        linkService.deleteById(id);

        verify(linkRepository, times(1)).deleteById(id);
    }

    @Test
    public void testGetByType() {
        String type = "someType";
        List<Link> links = new ArrayList<>();
        links.add(new Link());
        when(linkRepository.findByType(type)).thenReturn(links);

        List<Link> result = linkService.getByType(type);

        assertEquals(links.size(), result.size());
    }
}

