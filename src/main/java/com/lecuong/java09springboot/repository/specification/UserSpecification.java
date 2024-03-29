package com.lecuong.java09springboot.repository.specification;

import com.lecuong.java09springboot.entity.Role;
import com.lecuong.java09springboot.entity.User;
import com.lecuong.java09springboot.modal.request.user.UserFilterRequest;
import com.lecuong.java09springboot.modal.request.user.UserFilterWithListBlogRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class UserSpecification {

    public static Specification<User> filter(UserFilterRequest userFilterRequest) {
        return Specification.where(withUserName(userFilterRequest.getUserName()))
                .and(withAddress(userFilterRequest.getAddress()))
                .and(withEmail(userFilterRequest.getEmail()))
                .and(withCreatedBy(userFilterRequest.getCreatedBy()))
                .and(withStatus(userFilterRequest.getStatus()))
                .or(withUserNameLike(userFilterRequest.getUserName()));
    }

    public static Specification<User> filter(UserFilterWithListBlogRequest userFilterWithListBlogRequest) {
        return Specification.where(withListBlogId(userFilterWithListBlogRequest.getIds()));
    }

    public static Specification<User> withUserName(String userName) {
        if (StringUtils.isBlank(userName))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userName"), userName);
    }

    public static Specification<User> withAddress(String address) {
        if (StringUtils.isBlank(address))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("address"), address);
    }

    public static Specification<User> withEmail(String email) {
        if (StringUtils.isBlank(email))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<User> withUserNameLike(String userName) {
        if (StringUtils.isBlank(userName))
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("userName"), "%" + userName + "%");
    }

    private static Specification<User> withCreatedBy(String createdBy) {
        if (StringUtils.isEmpty(createdBy)) return null;

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("createdBy"), createdBy);
    }

    private static Specification<User> withStatus(Boolean status) {
        if (status == null) return null;

        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<User> withRoleId(Long roleId) {
        if (roleId == null || roleId == 0)
            return null;
        return (root, query, criteriaBuilder) -> {
            // Lay ra thong tin user thuoc roleId nao, su dung @ManyToMany thi lam nhu duoi
            query.distinct(true);
            Root<User> user = root;
            Root<Role> role = query.from(Role.class);
            Expression<Collection<User>> roleUsers = role.get("users");
            return criteriaBuilder.and(criteriaBuilder.equal(role.get("id"), roleId), criteriaBuilder.isMember(user, roleUsers));
        };
    }

    public static Specification<User> withBlogId(Long blogId) {
        if (blogId == null || blogId <= 0)
            return null;
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("blogs").get("id"), blogId);
    }

    public static Specification<User> withListBlogId(List<Long> blogIds) {
        if (CollectionUtils.isEmpty(blogIds))
            return null;
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return root.join("blogs").get("id").in(blogIds);
        };
    }
}
