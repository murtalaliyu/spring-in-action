package tacos.web.api;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path = "/api/tacos", produces = "application/json")
@CrossOrigin(origins = "http://tacocloud:8080")
public class TacoController {

  private TacoRepository tacoRepository;

  public TacoController(TacoRepository tacoRepository) {
    this.tacoRepository = tacoRepository;
  }

  @GetMapping(params = "recent")
  public Iterable<Taco> recentTacos() {
    PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
    return tacoRepository.findAll(page).getContent();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepository.findById(id);
    if (optTaco.isPresent()) return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRepository.save(taco);
  }

  @PutMapping(path = "/{tacoId}", consumes = "application/json")
  public Taco putTaco(@PathVariable("tacoId") Long tacoId, @RequestBody Taco taco) {
    taco.setId(tacoId);
    return tacoRepository.save(taco);
  }

  @PatchMapping(path = "/{tacoId}", consumes = "application/json")
  public Taco patchTaco(@PathVariable("tacoId") Long tacoId, @RequestBody Taco patch) {
    Taco taco = tacoRepository.findById(tacoId).get();
    if (patch.getName() != null) taco.setName(patch.getName());
    if (patch.getIngredients() != null) taco.setIngredients(patch.getIngredients());
    return tacoRepository.save(taco);
  }

  @DeleteMapping(path = "/{tacoId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTaco(@PathVariable("tacoId") Long tacoId) {
    try {
      tacoRepository.deleteById(tacoId);
    } catch (EmptyResultDataAccessException e) {}
  }
  
}
