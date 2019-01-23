package com.itsight.repository.specifications;

import com.itsight.domain.Cliente;
import com.itsight.util.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class UsuarioSpecification implements Specification<Cliente> {

    private SearchCriteria criteria;

    public UsuarioSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<")) {
            return builder.lessThanOrEqualTo(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                Expression<String> exp1 = builder.concat(root.<String>get("apellidoPaterno"), " ");
                exp1 = builder.concat(exp1, root.get("apellidoMaterno"));
                return builder.like(exp1, "%"+criteria.getValue().toString()+"%");
            } else {
                System.out.println(">22");
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("=")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                System.out.println(">1");
                return builder.equal(
                        root.<Boolean>get(criteria.getKey()), Boolean.valueOf(criteria.getValue().toString()));
            } else {
                System.out.println(">2");
                return builder.equal(root.get(criteria.getKey()), Boolean.valueOf(criteria.getValue().toString()));
            }
        }
        return null;
    }
}
