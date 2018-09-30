//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.openstack4j.openstack.storage.block.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.common.base.Preconditions;
import org.openstack4j.api.storage.BlockVolumeBackupService;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.storage.block.VolumeBackup;
import org.openstack4j.model.storage.block.VolumeBackupCreate;
import org.openstack4j.model.storage.block.VolumeBackupRestore;
import org.openstack4j.openstack.storage.block.domain.CinderVolumeBackup;
import org.openstack4j.openstack.storage.block.domain.CinderVolumeBackup.VolumeBackups;
import org.openstack4j.openstack.storage.block.domain.CinderVolumeBackupRestore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BlockVolumeBackupServiceImpl extends BaseBlockStorageServices implements BlockVolumeBackupService {
    public BlockVolumeBackupServiceImpl() {
    }

    public List<? extends VolumeBackup> list() {
        return ((VolumeBackups) this.get(VolumeBackups.class, new String[]{this.uri("/backups/detail", new Object[0])}).execute()).getList();
    }

    public List<? extends VolumeBackup> list(Map<String, String> filteringParams) {
        Invocation<VolumeBackups> invocation = this.buildInvocation("/backups/detail", filteringParams);
        return ((VolumeBackups) invocation.execute()).getList();
    }

    public List<? extends VolumeBackup> list(String tag, Map<String, String> filteringParams) {
        Invocation<VolumeBackups> invocation = this.buildInvocation("/backups/detail", filteringParams);
        return ((VolumeBackups) invocation.execute()).getList();
    }

    private Invocation<VolumeBackups> buildInvocation(String url, Map<String, String> filteringParams) {
        Invocation<VolumeBackups> invocation = this.get(VolumeBackups.class, new String[]{url});
        if (filteringParams == null) {
            return invocation;
        } else {
            Entry entry;
            for (Iterator var3 = filteringParams.entrySet().iterator(); var3.hasNext(); invocation = invocation.param((String) entry.getKey(), entry.getValue())) {
                entry = (Entry) var3.next();
            }

            return invocation;
        }
    }

    public VolumeBackup get(String backupId) {
        Preconditions.checkNotNull(backupId);
        return (VolumeBackup) this.get(CinderVolumeBackup.class, new String[]{this.uri("/backups/%s", new Object[]{backupId})}).execute();
    }

    public ActionResponse delete(String backupId) {
        Preconditions.checkNotNull(backupId);
        return (ActionResponse) this.deleteWithResponse(new String[]{this.uri("/backups/%s", new Object[]{backupId})}).execute();
    }

    public VolumeBackup create(VolumeBackupCreate vbc) {
        Preconditions.checkNotNull(vbc);
        Preconditions.checkNotNull(vbc.getVolumeId());
        return (VolumeBackup) this.post(CinderVolumeBackup.class, new String[]{this.uri("/backups", new Object[0])}).entity(vbc).execute();
    }

    public VolumeBackupRestore restore(String backupId, String name, String volumeId) {
        BlockVolumeBackupServiceImpl._VolumeBackupRestore entity = new BlockVolumeBackupServiceImpl._VolumeBackupRestore(name, volumeId);
        return (VolumeBackupRestore) this.post(CinderVolumeBackupRestore.class, new String[]{this.uri("/backups/%s/restore", new Object[]{backupId})}).entity(entity).execute();
    }

    @JsonRootName("restore")
    private static class _VolumeBackupRestore implements ModelEntity {
        private static final long serialVersionUID = 1L;
        @JsonProperty("name")
        private String name;
        @JsonProperty("volume_id")
        private String volumeId;

        public _VolumeBackupRestore(String name, String volumeId) {
            this.name = name;
            this.volumeId = volumeId;
        }
    }
}
