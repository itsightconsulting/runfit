package com.itsight.repository.specifications;

import com.itsight.domain.Cliente;
import com.itsight.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class UsuarioSpecificationBuilder {

    private final List<SearchCriteria> params;

    public UsuarioSpecificationBuilder() {
        params = new ArrayList<SearchCriteria>();
    }

    public UsuarioSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Cliente> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification<Cliente>> specs = new ArrayList<>();
        for (SearchCriteria param : params) {
            specs.add(new UsuarioSpecification(param));
        }

        Specification<Cliente> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}
