package mn.ezpay.controller;

import mn.ezpay.entity.cards;
import mn.ezpay.service.cardService;
import mn.ezpay.service.tokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

@RestController
public class cardsController {

    @Autowired
    cardService service;

    @Autowired
    tokenService tokenService;

    @RequestMapping(value = "card/save", method = RequestMethod.POST, consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public cards save(@RequestBody cards entity) {
        if ("active".equals(entity.getStatus())) {
            service.update(entity);
        } else {
            entity = tokenService.confirmCard(entity);
            if (entity.getPpin() != null && entity.getPpin().length() > 0) {
                service.save(entity);
                return entity;
            } else {
                service.save(entity);
                return entity;
            }
        }

        return null;
    }

    @RequestMapping(value = "card/delete", method = RequestMethod.DELETE)
    public cards delete(@RequestParam int id) {
        cards item = service.findOne(id);
        service.delete(item);
        return item;
    }

    @RequestMapping(value = "card/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
    public cards update(@RequestBody cards entity) {
        if ("active".equals(entity.getStatus())) {
            service.update(entity);
        } else {
            entity = tokenService.confirmCard(entity);
            if (entity.getPpin() != null && entity.getPpin().length() > 0) {
                service.update(entity);
                return entity;
            }
        }

        return null;
    }

    @RequestMapping(value = "card/activate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public cards activate(@RequestParam int id, @RequestParam String pin) {
        return service.activate(id, pin);
    }

    @RequestMapping(value = "card/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Hashtable findAll(@RequestParam int page, @RequestParam int size, @RequestParam String order, @RequestParam String dir) {
        List list = service.findAll(page, size, order, dir);
        Hashtable pageable = new Hashtable();
        pageable.put("total", service.total());
        pageable.put("data", list);
        return pageable;
    }

}
