package pe.jsaire.springtravel.services.abstract_service;

public interface ISimpleCrudService<RS, RQ, ID> {

    RS findById(ID id);

    RS save(RQ rq);

    void deleteById(ID id);
}
