package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    private Order order;
    private DateTime currentTime;

    @Mock
    private Clock clockMock;

    @Before public void setUp() throws Exception {
        order = new Order(clockMock);
        currentTime = DateTime.now();

        when(clockMock.getDateTime()).thenReturn(currentTime);
    }

    @Test public void OrderCreatedState() {
        assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

    @Test public void OrderCreatedStateWithOneItem() {
        order.addItem(new OrderItem());
        assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

    @Test public void OrderCreatedStateWithAddedItemAfterSubmit() {
        order.submit();
        order.addItem(new OrderItem());
        assertThat(order.getOrderState(), is(Order.State.CREATED));
    }

    @Test public void OrderSubmittedState() {
        order.submit();
        assertThat(order.getOrderState(), is(Order.State.SUBMITTED));
    }

    @Test public void OrderConfirmedState () {
        order.submit();
        order.confirm();

        assertThat(order.getOrderState(), is(Order.State.CONFIRMED));
    }

    @Test public void OrderConfirmThrowOrderExpiredException () {
        order.submit();
        when(clockMock.getDateTime()).thenReturn(currentTime.plusDays(2));

        assertThrows(OrderExpiredException.class, () -> order.confirm());
    }

    @Test public void OrderCancelledState () {
        order.submit();
        when(clockMock.getDateTime()).thenReturn(currentTime.plusDays(2));

        assertThrows(OrderExpiredException.class, () -> order.confirm());
        assertThat(order.getOrderState(), is(Order.State.CANCELLED));
    }

    @Test public void OrderRealizedState () {
        order.submit();
        order.confirm();
        order.realize();

        assertThat(order.getOrderState(), is(Order.State.REALIZED));
    }

    @Test public void OrderThrowOrderStateException () {
        assertThrows(OrderStateException.class, () -> order.realize());
    }
}
