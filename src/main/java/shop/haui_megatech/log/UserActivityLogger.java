package shop.haui_megatech.log;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.Status;
import shop.haui_megatech.domain.entity.ActivityLog;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.repository.ActivityLogRepository;
import shop.haui_megatech.utility.AuthenticationUtil;

import java.util.Map;

import static java.util.Map.entry;

@Aspect
@Component
@RequiredArgsConstructor
public class UserActivityLogger {
    private final ActivityLogRepository activityLogRepository;
    private final Map<String, String>   operationByMethodName = Map.ofEntries(
            entry("addOneUser", "log.add-one-user"),
            entry("importExcelUser", "log.import-excel-user"),
            entry("importCsvUser", "log.import-csv-user"),
            entry("updateInfoUser", "log.update-info-user"),
            entry("updatePasswordUser", "log.update-password-user"),
            entry("softDeleteOneUser", "log.soft-delete-one-user"),
            entry("softDeleteListUser", "log.soft-delete-list-users"),
            entry("hardDeleteOneUser", "log.hard-delete-one-user"),
            entry("hardDeleteListUsers", "log.hard-delete-list-users"),
            entry("restoreOneUser", "log.restore-one-user"),
            entry("restoreListUsers", "log.restore-list-user"),
            entry("resetPasswordOneUser", "log.reset-password-one-user"),
            entry("resetPasswordListUser", "log.reset-password-list-user")
    );

    @Around("execution(* shop.haui_megatech.service.*.*(..))")
    public Object logUserActivity(ProceedingJoinPoint joinPoint) throws Throwable {
        User requestedUser = null;

        try {
            requestedUser = AuthenticationUtil.getRequestedUser();
        } catch (Exception e) {}
        Object result = joinPoint.proceed();
        String loggedOperation = operationByMethodName.get(joinPoint.getSignature().getName());
        if (loggedOperation != null
            && requestedUser != null
            && result != null
            && result instanceof GlobalResponseDTO
        ) {
            activityLogRepository.save(
                    ActivityLog.builder()
                               .subject(requestedUser)
                               .operation(loggedOperation)
                               .success(GlobalResponseDTO.class.cast(result).meta().status().equals(Status.SUCCESS))
                               .build()
            );
        }
        return result;
    }
}
