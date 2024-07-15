package com.vt.project.intercepter;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.vt.project.model.PerRoleMap;
import com.vt.project.model.User;
import com.vt.project.model.UserRole;
import com.vt.project.model.UserRoleMap;
import com.vt.project.repository.perRoleMapRepo;
import com.vt.project.repository.permissionRepo;
import com.vt.project.repository.userRepo;
import com.vt.project.repository.userRoleMapRepo;
import com.vt.project.repository.userRoleRepo;
import com.vt.project.utils.JwtUtils;
import com.vt.project.utils.LogicEnum;
import com.vt.project.utils.Permission;
import com.vt.project.utils.PermissionsEnum;
import com.vt.project.utils.RolePermissionMapId;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Value("${jwt.secret}")
	private String key;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	private userRepo userRepo;

	@Autowired
	private perRoleMapRepo perRoleMapRepo;

	@Autowired
	private userRoleMapRepo userRoleMapRepo;

	@Autowired
	userRoleRepo userRoleRepo;

	@Autowired
	permissionRepo permissionRepo;

	private Key getSigningKey() {
		byte[] keyBytes = this.key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String bearerToken = request.getHeader("Authorization");

		System.out.println(bearerToken);

		if (bearerToken != null) {
			String emailString = getUsernameFromToken(bearerToken.substring(7, bearerToken.length()));

			User user = userRepo.findByEmail(emailString);

			HandlerMethod hm;
			try {
				hm = (HandlerMethod) handler;
			} catch (ClassCastException e) {
				return preHandle(request, response, handler);
			}
			Method method = hm.getMethod();

			if (method.isAnnotationPresent(Permission.class)) {

				Permission permissionAnnotation = method.getAnnotation(Permission.class);

				LogicEnum type = permissionAnnotation.type();
				PermissionsEnum[] permissions = permissionAnnotation.permissions();

				if (type == LogicEnum.All) {

					boolean hasAllPermissions = checkIfUserHasAllPermissions(permissions, user);
					if (!hasAllPermissions) {
						throw new SecurityException("User does not have all required permissions");
					}
				} else if (type == LogicEnum.Any) {

					boolean hasAnyPermission = checkIfUserHasAnyPermission(permissions, user);
					if (!hasAnyPermission) {
						throw new SecurityException("User does not have any required permission");
					}
				}

			}
		}

		return true;
	}

	private boolean checkIfUserHasAllPermissions(PermissionsEnum[] permissions, User user) {

		UserRoleMap userRoleMap = userRoleMapRepo.findByUid(user.getId());

		UserRole userRole = userRoleRepo.findById(userRoleMap.getUid());

		List<PerRoleMap> perRole = perRoleMapRepo.findByRid(userRole.getId());

		List<com.vt.project.model.Permission> permList = new ArrayList<com.vt.project.model.Permission>();

		if (perRole != null) {
			for (PerRoleMap pid : perRole) {
				com.vt.project.model.Permission permission = permissionRepo.findById(pid.getId().getPid());
				permList.add(permission);
			}
		}

		String[] pers = permList.stream().map(per -> per.getName()).toArray(String[]::new);

		Set<String> persSet = new HashSet<>(Arrays.asList(pers)); // Convert to Set for efficient lookup
		System.out.println(persSet.toString());

		for (PermissionsEnum element : permissions) {
			String elementStr = element.name(); // Get the name of the enum
			System.out.println(element.name());
			if (!persSet.contains(elementStr)) {
				return false; // Return false if any permission is missing
			}
		}

		return true;
	}

	private boolean checkIfUserHasAnyPermission(PermissionsEnum[] permissions, User user) {
		UserRoleMap userRoleMap = userRoleMapRepo.findByUid(user.getId());

		UserRole userRole = userRoleRepo.findById(userRoleMap.getUid());

		List<PerRoleMap> perRole = perRoleMapRepo.findByRid(userRole.getId());

		List<com.vt.project.model.Permission> permList = new ArrayList<com.vt.project.model.Permission>();

		if (perRole != null) {
			for (PerRoleMap pid : perRole) {
				com.vt.project.model.Permission permission = permissionRepo.findById(pid.getId().getPid());
				permList.add(permission);
			}
		}

		String[] pers = permList.stream().map(per -> per.getName()).toArray(String[]::new);

		boolean hasCommonElement = false;

		for (Object element : pers) {
			for (Object array2Element : permissions) {
				if (element.equals(array2Element.toString())) {
					hasCommonElement = true;
					break; // Exit the inner loop as soon as a match is found
				}
			}
			if (hasCommonElement) {
				break; // Exit the outer loop if a common element has been found
			}
		}

		return hasCommonElement;
	}

	public String getUsernameFromToken(String token) {
		try {

			Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);

			return jws.getBody().getSubject();

		} catch (JwtException | JSONException e) {
			// Handle exceptions, e.g., token is invalid or expired
			throw new RuntimeException("Failed to extract username from JWT token");
		}
	}
}
