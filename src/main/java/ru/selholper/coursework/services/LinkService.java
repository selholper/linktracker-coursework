package ru.selholper.coursework.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.selholper.coursework.models.Link;
import ru.selholper.coursework.repositories.LinkRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LinkService {

    private final LinkRepository linkRepository;

    @Autowired
    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Optional<Link> findById(int id) {
        return linkRepository.findById(id);
    }

    public Link getById(int id) {
        return linkRepository.getById(id);
    }

    public void deleteById(int id) {
        linkRepository.deleteById(id);
    }

    public List<Link> getByType(String type) {
        return linkRepository.findByType(type);
    }
}
