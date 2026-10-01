package farid.aghazada.song_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import farid.aghazada.song_service.Entity.SongMetadataEntity;

@Repository
public interface SongRepository extends JpaRepository<SongMetadataEntity, Integer>{

}
