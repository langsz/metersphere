package io.metersphere.base.mapper.ext;

import io.metersphere.base.domain.Organization;
import io.metersphere.base.domain.Project;
import io.metersphere.controller.request.ProjectRequest;
import io.metersphere.dto.ProjectDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ExtProjectMapper {

    List<ProjectDTO> getProjectWithWorkspace(@Param("proRequest") ProjectRequest request);

    List<String> getProjectIdByWorkspaceId(String workspaceId);

    int removeIssuePlatform(@Param("platform") String platform, @Param("orgId") String orgId);

    List<ProjectDTO> getUserProject(@Param("proRequest") ProjectRequest request);

    String getSystemIdByProjectId(String projectId);

    List<String> getProjectIds();

    String getMaxSystemId();

    @MapKey("id")
    Map<String, Project> queryNameByIds(@Param("ids") List<String> ids);

    Organization getOrganizationByProjectId(@Param("projectId")String projectId);

    List<Integer> selectTcpPorts();
}
