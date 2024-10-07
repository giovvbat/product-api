package com.giovanna.demo.service;

import com.giovanna.demo.dto.role.RoleRecordDto;
import com.giovanna.demo.enums.UserAuthority;
import com.giovanna.demo.infra.exception.role.NoRolesFoundException;
import com.giovanna.demo.infra.exception.role.RoleNameAlreadyTakenException;
import com.giovanna.demo.infra.exception.role.RoleNotFoundException;
import com.giovanna.demo.model.RoleModel;
import com.giovanna.demo.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public RoleModel saveRole(RoleRecordDto roleRecordDto) {
        if (roleRepository.findByRoleName(roleRecordDto.name()).isPresent()) {
            throw new RoleNameAlreadyTakenException();
        }

        RoleModel role = new RoleModel();
        role.setRoleName(roleRecordDto.name());
        role.setAuthorities(roleRecordDto.authorities());

        return roleRepository.save(role);
    }

    @Transactional
    public RoleModel saveFirstAdminRole() {
        String roleName = "first-admin";

        if (roleRepository.findByRoleName(roleName).isPresent()) {
            return roleRepository.findByRoleName(roleName).get();
        }

        RoleModel role = new RoleModel();
        role.setRoleName(roleName);
        Set<UserAuthority> authorities = new HashSet<>();
        authorities.add(UserAuthority.ADMIN);
        role.setAuthorities(authorities);

        System.out.println("creating first admin role");

        return roleRepository.save(role);
    }

    public RoleModel getRoleById(UUID id) {
        return roleRepository.findById(id).
                orElseThrow(RoleNotFoundException::new);
    }

    public List<RoleModel> getAllRoles() {
        List<RoleModel> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            throw new NoRolesFoundException();
        }

        return roles;
    }

    @Transactional
    public RoleModel updateRole(UUID id, RoleRecordDto roleRecordDto) {
        RoleModel role = roleRepository.findById(id)
                .orElseThrow(RoleNotFoundException::new);

        if (roleRepository.findByRoleName(roleRecordDto.name()).isPresent()) {
            throw new RoleNameAlreadyTakenException();
        }

        BeanUtils.copyProperties(roleRecordDto, role);
        return roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(UUID id) {
        RoleModel role = roleRepository.findById(id)
                .orElseThrow(RoleNotFoundException::new);

        roleRepository.deleteById(id);
    }
}
