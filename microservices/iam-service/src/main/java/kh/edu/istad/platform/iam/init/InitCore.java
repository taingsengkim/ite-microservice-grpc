package kh.edu.istad.platform.iam.init;


import kh.edu.istad.platform.iam.domain.Permission;
import kh.edu.istad.platform.iam.repository.PermissionRepostitory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InitCore {
    private final PermissionRepostitory permissionRepostitory;
}
