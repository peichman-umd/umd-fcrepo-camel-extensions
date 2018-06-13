package edu.umd.lib.fcrepo.camel.broadcast;

import org.apache.camel.builder.RouteBuilder;

import edu.umd.lib.osgi.service.AbstractManagedServiceInstance;

/**
 * A broadcast service instance, typically created and controlled via the BroadcastFactory.
 */
public class BroadcastDispatcher extends AbstractManagedServiceInstance {
  private String inputStream;
  private String messageRecipients;

  public BroadcastDispatcher() {
  }

  /**
   * Returns a RouteBuilder, using the values from the instance variables
   *
   * @return
   * @throws Exception
   */
  @Override
  protected RouteBuilder buildRoute() throws Exception {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from(inputStream)
            .routeId("Broadcast " + inputStream)
            .description("Broadcast messages from one queue/topic to other specified queues/topics.")
            .log("Distributing message: ${headers[org.fcrepo.jms.timestamp]}: " +
                "${headers[org.fcrepo.jms.identifier]}:${headers[org.fcrepo.jms.eventType]}")
            .recipientList(simple(messageRecipients)).ignoreInvalidEndpoints();
      }
    };
  }

  /**
   * Sets the input stream for the route.
   *
   * @param inputStream
   *          the input stream for the route
   */
  public void setInputStream(String inputStream) {
    this.inputStream = inputStream;
  }

  /**
   * Sets the message recipients for the route.
   *
   * @param messageRecipients
   *          the message recipients for the route
   */
  public void setMessageRecipients(String messageRecipients) {
    this.messageRecipients = messageRecipients;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(this.getClass().getName() + " [inputStream=");
    builder.append(inputStream);
    builder.append(", messageRecipients=");
    builder.append(messageRecipients);
    builder.append(", routeId=");
    builder.append(routeId);
    builder.append("]");
    return builder.toString();
  }
}
