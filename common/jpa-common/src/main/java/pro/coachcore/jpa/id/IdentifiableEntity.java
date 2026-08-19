package pro.coachcore.jpa.id;

public interface IdentifiableEntity<T> {
  T getLocalId();
  void setLocalId(T localId);
}
