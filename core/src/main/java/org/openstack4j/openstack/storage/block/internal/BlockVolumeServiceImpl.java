package org.openstack4j.openstack.storage.block.internal;

import org.openstack4j.api.Apis;
import org.openstack4j.api.Builders;
import org.openstack4j.api.storage.BlockVolumeService;
import org.openstack4j.api.storage.BlockVolumeTransferService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.storage.block.Volume;
import org.openstack4j.model.storage.block.VolumeType;
import org.openstack4j.model.storage.block.VolumeUploadImage;
import org.openstack4j.model.storage.block.options.UploadImageData;
import org.openstack4j.openstack.storage.block.domain.*;
import org.openstack4j.openstack.storage.block.domain.CinderVolume.Volumes;
import org.openstack4j.openstack.storage.block.domain.CinderVolumeType.VolumeTypes;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Manages Volumes and Volume Type based operations against Block Storage (Cinder)
 *
 * @author Jeremy Unruh
 */
public class BlockVolumeServiceImpl extends BaseBlockStorageServices implements BlockVolumeService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends VolumeType> listVolumeTypes() {
        return get(VolumeTypes.class, uri("/types")).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Volume> list() {
        return get(Volumes.class, uri("/volumes/detail")).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Volume> list(Map<String, String> filteringParams) {
        Invocation<Volumes> volumeInvocation = buildInvocation("/volumes/detail", filteringParams);
        return volumeInvocation.execute().getList();
    }

    public List<? extends Volume> list(String tag, Map<String, String> filteringParams) {
        Invocation<Volumes> volumeInvocation = buildInvocation("/volumes/detail", filteringParams);
        return volumeInvocation.execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Volume get(String volumeId) {
        checkNotNull(volumeId);
        return get(CinderVolume.class, uri("/volumes/%s", volumeId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String volumeId) {
        checkNotNull(volumeId);
        return deleteWithResponse(uri("/volumes/%s", volumeId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse forceDelete(String volumeId) {
        checkNotNull(volumeId);
        return post(ActionResponse.class, uri("/volumes/%s/action", volumeId))
                .entity(new ForceDeleteAction())
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse resetState(String volumeId, Volume.Status status) {
        checkNotNull(volumeId);
        checkNotNull(status);
        return post(ActionResponse.class, uri("/volumes/%s/action", volumeId))
                .entity(new ResetStatusAction(status))
                .execute();
    }

    public ActionResponse bootable(String volumeId, boolean bootable) {
        return post(ActionResponse.class, uri("/volumes/%s/action", volumeId))
                .entity(new BootableStatusAction(bootable))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse extend(String volumeId, Integer newSize) {
        checkNotNull(volumeId);
        checkNotNull(newSize);
        return post(ActionResponse.class, uri("/volumes/%s/action", volumeId))
                .entity(new ExtendAction(newSize))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Volume create(Volume volume) {
        checkNotNull(volume);
        return post(CinderVolume.class, uri("/volumes")).entity(volume).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse update(String volumeId, String name, String description) {
        checkNotNull(volumeId);
        if (name == null && description == null)
            return ActionResponse.actionFailed("Name and Description are both required", 412);

        return put(ActionResponse.class, uri("/volumes/%s", volumeId))
                .entity(Builders.volume().name(name).description(description).build())
                .execute();
    }

    public ActionResponse update(String volumeId, String name, String description, boolean bootable) {
        checkNotNull(volumeId);
        if (name == null && description == null)
            return ActionResponse.actionFailed("Name and Description are both required", 412);

        return put(ActionResponse.class, uri("/volumes/%s", volumeId))
                .entity(Builders.volume().name(name).description(description).bootable(bootable).build())
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteVolumeType(String volumeTypeId) {
        checkNotNull(volumeTypeId);
        delete(Void.class, uri("/types/%s", volumeTypeId)).execute();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VolumeType createVolumeType(VolumeType volumeType) {
        checkNotNull(volumeType);
        return post(CinderVolumeType.class, uri("/types")).entity(volumeType).execute();
    }

    @Override
    public ActionResponse migrate(String volumeId, String hostService, boolean forceHostCopy) {
        CinderVolumeMigration migration = new CinderVolumeMigration(hostService, forceHostCopy);
        return post(ActionResponse.class, uri("/volumes/%s/action", volumeId))
                .entity(migration)
                .execute();
    }

    @Override
    public VolumeUploadImage uploadToImage(String volumeId, UploadImageData data) {
        checkNotNull(volumeId, "volumeId");
        checkNotNull(data, "UploadImageData");

        return post(CinderVolumeUploadImage.class, uri("/volumes/%s/action", volumeId))
                .entity(CinderUploadImageData.create(data))
                .execute();
    }

    @Override
    public BlockVolumeTransferService transfer() {
        return Apis.get(BlockVolumeTransferService.class);
    }

    private Invocation<Volumes> buildInvocation(String uri, Map<String, String> filteringParams) {
        Invocation<Volumes> volumeInvocation = get(Volumes.class, uri);
        if (filteringParams == null) {
            return volumeInvocation;
        } else {
            for (Map.Entry<String, String> entry : filteringParams.entrySet()) {
                volumeInvocation = volumeInvocation.param(entry.getKey(), entry.getValue());
            }
        }
        return volumeInvocation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse readOnlyModeUpdate(String volumeId, boolean readonly) {
        checkNotNull(volumeId);
        return post(ActionResponse.class, uri("/volumes/%s/action", volumeId))
                .entity(new UpdateReadOnlyFlagAction(readonly))
                .execute();
    }

}
