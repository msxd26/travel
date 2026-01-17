package pe.jsaire.springtravel.services.abstract_service;

public interface ICrudService<RS, RQ, ID> {

    RS findById(ID id);

    RS save(RQ rq);

    RS update(ID id, RQ rq);

    void deleteById(ID id);

}
