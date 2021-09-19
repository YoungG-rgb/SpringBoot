package com.crudonspringboot.dao;


import com.crudonspringboot.models.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(entityManager.createQuery("select e from Role e", Role.class).getResultList());
    }

    @Override
    public Role getRoleById(Long id) {
        return entityManager.find(Role.class,id);
    }

}
